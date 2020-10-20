package com.toolplat.generator.plugins;

import com.toolplat.generator.plugins.constant.PluginConstants;
import org.mybatis.generator.api.ConnectionFactory;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.db.ActualTableName;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import static org.mybatis.generator.internal.util.StringUtility.stringContainsSpace;

/**
 * mgb 基础plugin
 *
 * @author suyin
 */
public class BasePlugin extends PluginAdapter {

    /**
     * 表中所有唯一索引的集合
     */
    Map<String, List<IntrospectedColumn>> uks ;

    /**
     * 指定用来构建selectByUniqueKey等方法的唯一索引
     */
    List<IntrospectedColumn> uniqueKey;


    /**
     * 严格按照数据库顺序的allColumns
     */
    List<IntrospectedColumn> allColumns;



    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
        this.uks = null;
        try {
            initUniqueKey(introspectedTable);
            initAllColumns(introspectedTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object uks = introspectedTable.getAttribute(PluginConstants.TABLE_UNIQUE_KEYS);
        if(null != uks){
            this.uks = (Map<String, List<IntrospectedColumn>> )uks;
        }
        Object uk = introspectedTable.getAttribute(PluginConstants.TABLE_PROPERTY_UNIQUE_KEY);
        if(null != uk){
            this.uniqueKey = (List<IntrospectedColumn>) uk;
        }

    }

    /**
     * 按照数据库顺序的all columns
     * @param it
     */
    private void initAllColumns(IntrospectedTable it) throws SQLException {
        Connection connection = getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        TableConfiguration tc = it.getTableConfiguration();
        ActualTableName tb = getTableInfo(metaData, tc);
        // uks Map<INDEX_NAME,Set<COLUMN_NAME>>
        Map<String, List<IntrospectedColumn>> uks = new HashMap<>(5);
        ResultSet rs = metaData.getColumns(tb.getCatalog(), tb.getSchema(), tb.getTableName(),null);
        allColumns = new ArrayList<>();
        while (rs.next()) {
            // @see https://docs.oracle.com/javase/6/docs/api/java/sql/DatabaseMetaData.html#getColumns(java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String)
            Optional<IntrospectedColumn> columnOptional =  it.getColumn(rs.getString("COLUMN_NAME"));
            if(!columnOptional.isPresent()){
                continue;
            }
           allColumns.add(columnOptional.get());
        }
    }

    /**
     * 增加unique key 特殊属性
     *
     * @param it
     */
    private void initUniqueKey(IntrospectedTable it) throws SQLException {
        Connection connection = getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        TableConfiguration tc = it.getTableConfiguration();
        ActualTableName tb = getTableInfo(metaData, tc);
//        table.getIntrospectedCatalog(), table
//                .getIntrospectedSchema(), table
//                .getIntrospectedTableName()

        // uks Map<INDEX_NAME,Set<COLUMN_NAME>>
        Map<String, List<IntrospectedColumn>> uks = new HashMap<>(5);
        ResultSet rs = metaData.getIndexInfo(tb.getCatalog(), tb.getSchema(), tb.getTableName(), true, false);
        while (rs.next()) {
            // @see https://docs.oracle.com/javase/6/docs/api/java/sql/DatabaseMetaData.html#getColumns(java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String)
            String indexName = rs.getString("INDEX_NAME");
            if("PRIMARY".equals(indexName)){
                continue;
            }
            Optional<IntrospectedColumn> columnOptional =  it.getColumn(rs.getString("COLUMN_NAME"));
            if(!columnOptional.isPresent()){
                continue;
            }
            if(uks.containsKey(indexName)){
                uks.get(indexName).add(columnOptional.get());
            }else{
                List<IntrospectedColumn> cols = new ArrayList<>();
                cols.add(columnOptional.get());
                uks.put(indexName, cols);
            }
        }
        it.setAttribute(PluginConstants.TABLE_UNIQUE_KEYS,uks);
        it.setAttribute(PluginConstants.TABLE_PROPERTY_UNIQUE_KEY,getOnlyOneUniqueKey(it, uks));
    }

    private List<IntrospectedColumn> getOnlyOneUniqueKey(IntrospectedTable it, Map<String, List<IntrospectedColumn>> uks) {
        if(null == uks || uks.size() == 0){
            return null;
        }
        String uniqueKey = it.getTableConfiguration().getProperty(PluginConstants.TABLE_PROPERTY_UNIQUE_KEY);
        if(null != uniqueKey && uniqueKey.length() > 0){
            return uks.get(uniqueKey);
        }
        // 如果没有配置table的property.uniqueKey,选择返回默认第一个（区分有主键和没有主键的场景）
        Map<String, List<IntrospectedColumn>> uksBak = new HashMap<>(uks);
        List<IntrospectedColumn> pk = uksBak.get(PluginConstants.PRIMARY_KEY_INDEX_NAME);
        uksBak.remove(PluginConstants.PRIMARY_KEY_INDEX_NAME);

        if(uksBak.size() == 0){
            // 除主键外没有其他唯一索引
            return pk;
        }else  if(uksBak.size() == 1){
            // 除主键外其他唯一索引只有一个，选择这个唯一索引
            return uksBak.entrySet().iterator().next().getValue();
        } else{
            // 除主键外其他唯一索引有多个，选第一个，并warning日志
            Map.Entry<String, List<IntrospectedColumn>> entry = uksBak.entrySet().iterator().next();
            System.out.println("table :" + it.getFullyQualifiedTableNameAtRuntime() +" has more then one unique key, " +
                    "select first one ["+entry.getKey()+"] as uniqueKey.");
            System.out.println("if you want assign one, use property in table like this ");
            System.out.println("<table tableName=\"tableName\" domainObjectName=\"TableName\">\n" +
                    "            <property name=\"uniqueKey\" value=\"your unique key name\"/>\n" +
                    "        </table>");
            return entry.getValue();
        }


    }

    private ActualTableName getTableInfo(DatabaseMetaData databaseMetaData, TableConfiguration tc) throws SQLException {
        String localCatalog;
        String localSchema;
        String localTableName;

        boolean delimitIdentifiers = tc.isDelimitIdentifiers()
                || stringContainsSpace(tc.getCatalog())
                || stringContainsSpace(tc.getSchema())
                || stringContainsSpace(tc.getTableName());

        if (delimitIdentifiers) {
            localCatalog = tc.getCatalog();
            localSchema = tc.getSchema();
            localTableName = tc.getTableName();
        } else if (databaseMetaData.storesLowerCaseIdentifiers()) {
            localCatalog = tc.getCatalog() == null ? null : tc.getCatalog()
                    .toLowerCase();
            localSchema = tc.getSchema() == null ? null : tc.getSchema()
                    .toLowerCase();
            localTableName = tc.getTableName() == null ? null : tc
                    .getTableName().toLowerCase();
        } else if (databaseMetaData.storesUpperCaseIdentifiers()) {
            localCatalog = tc.getCatalog() == null ? null : tc.getCatalog()
                    .toUpperCase();
            localSchema = tc.getSchema() == null ? null : tc.getSchema()
                    .toUpperCase();
            localTableName = tc.getTableName() == null ? null : tc
                    .getTableName().toUpperCase();
        } else {
            localCatalog = tc.getCatalog();
            localSchema = tc.getSchema();
            localTableName = tc.getTableName();
        }

        if (tc.isWildcardEscapingEnabled()) {
            String escapeString = databaseMetaData.getSearchStringEscape();

            StringBuilder sb = new StringBuilder();
            StringTokenizer st;
            if (localSchema != null) {
                st = new StringTokenizer(localSchema, "_%", true); //$NON-NLS-1$
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    if (token.equals("_") //$NON-NLS-1$
                            || token.equals("%")) { //$NON-NLS-1$
                        sb.append(escapeString);
                    }
                    sb.append(token);
                }
                localSchema = sb.toString();
            }

            sb.setLength(0);
            st = new StringTokenizer(localTableName, "_%", true); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (token.equals("_") //$NON-NLS-1$
                        || token.equals("%")) { //$NON-NLS-1$
                    sb.append(escapeString);
                }
                sb.append(token);
            }
            localTableName = sb.toString();
        }

        return new ActualTableName(localCatalog, localSchema,
                localTableName);
    }

    /**
     * 根据context获取connection
     *
     * @return
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        ConnectionFactory connectionFactory;
        if (context.getJdbcConnectionConfiguration() != null) {
            connectionFactory = new JDBCConnectionFactory(context.getJdbcConnectionConfiguration());
        } else {
            connectionFactory = ObjectFactory.createConnectionFactory(context);
        }

        return connectionFactory.getConnection();
    }
}
