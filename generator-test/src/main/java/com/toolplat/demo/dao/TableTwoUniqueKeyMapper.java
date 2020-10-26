package com.toolplat.demo.dao;

import com.toolplat.demo.domain.TableTwoUniqueKeyPO;
import com.toolplat.demo.domain.TableTwoUniqueKeyPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableTwoUniqueKeyMapper {
    /**
     * auto method:countByExample
     * @param example
     * @return long
     */
    long countByExample(TableTwoUniqueKeyPOExample example);

    /**
     * auto method:deleteByExample
     * @param example
     * @return int
     */
    int deleteByExample(TableTwoUniqueKeyPOExample example);

    /**
     * auto method:deleteByPrimaryKey
     * @param orgId
     * @param code
     * @return int
     */
    int deleteByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:insert
     * @param record
     * @return int
     */
    int insert(TableTwoUniqueKeyPO record);

    /**
     * auto method:insertSelective
     * @param record
     * @return int
     */
    int insertSelective(TableTwoUniqueKeyPO record);

    /**
     * auto method:selectByExample
     * @param example
     * @return List<TableTwoUniqueKeyPO>
     */
    List<TableTwoUniqueKeyPO> selectByExample(TableTwoUniqueKeyPOExample example);

    /**
     * auto method:selectByExampleForUpdate
     * @param example
     * @return TableTwoUniqueKeyPO
     */
    TableTwoUniqueKeyPO selectByExampleForUpdate(TableTwoUniqueKeyPOExample example);

    /**
     * auto method:selectByPrimaryKey
     * @param orgId
     * @param code
     * @return TableTwoUniqueKeyPO
     */
    TableTwoUniqueKeyPO selectByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:selectByPrimaryKeyForUpdate
     * @param orgId
     * @param cid
     * @return TableTwoUniqueKeyPO
     */
    TableTwoUniqueKeyPO selectByPrimaryKeyForUpdate(@Param("orgId") Long orgId, @Param("cid") String cid);

    /**
     * auto method:updateByExampleSelective
     * @param record
     * @param example
     * @return int
     */
    int updateByExampleSelective(@Param("record") TableTwoUniqueKeyPO record, @Param("example") TableTwoUniqueKeyPOExample example);

    /**
     * auto method:updateByExample
     * @param record
     * @param example
     * @return int
     */
    int updateByExample(@Param("record") TableTwoUniqueKeyPO record, @Param("example") TableTwoUniqueKeyPOExample example);

    /**
     * auto method:updateByPrimaryKeySelective
     * @param record
     * @return int
     */
    int updateByPrimaryKeySelective(TableTwoUniqueKeyPO record);

    /**
     * auto method:updateByPrimaryKey
     * @param record
     * @return int
     */
    int updateByPrimaryKey(TableTwoUniqueKeyPO record);

    /**
     * auto method:selectByUniqueKey
     * @param orgId
     * @param cid
     * @return TableTwoUniqueKeyPO
     */
    TableTwoUniqueKeyPO selectByUniqueKey(@Param("orgId") Long orgId, @Param("cid") String cid);

    /**
     * auto method:selectByUniqueKeyForUpdate
     * @param orgId
     * @param cid
     * @return TableTwoUniqueKeyPO
     */
    TableTwoUniqueKeyPO selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("cid") String cid);

    /**
     * auto method:updateByUniqueKeySelective
     * @param record
     * @return int
     */
    int updateByUniqueKeySelective(TableTwoUniqueKeyPO record);

    /**
     * auto method:deleteByUniqueKey
     * @param orgId
     * @param cid
     * @return int
     */
    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("cid") String cid);

    /**
     * auto method:batchInsert
     * @param list
     * @return int
     */
    int batchInsert(@Param("list") List<TableTwoUniqueKeyPO> list);

    /**
     * auto method:upsertByPrimaryKey
     * @param record
     * @return int
     */
    int upsertByPrimaryKey(TableTwoUniqueKeyPO record);

    /**
     * auto method:upsertByUniqueKey
     * @param record
     * @return int
     */
    int upsertByUniqueKey(TableTwoUniqueKeyPO record);
}