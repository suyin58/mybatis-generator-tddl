package com.toolplat.demo.dao;

import com.toolplat.demo.domain.TableOneUniqueKeyPO;
import com.toolplat.demo.domain.TableOneUniqueKeyPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableOneUniqueKeyMapper {
    /**
     * auto method:countByExample
     * @param example
     * @return long
     */
    long countByExample(TableOneUniqueKeyPOExample example);

    /**
     * auto method:deleteByExample
     * @param example
     * @return int
     */
    int deleteByExample(TableOneUniqueKeyPOExample example);

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
    int insert(TableOneUniqueKeyPO record);

    /**
     * auto method:insertSelective
     * @param record
     * @return int
     */
    int insertSelective(TableOneUniqueKeyPO record);

    /**
     * auto method:selectByExample
     * @param example
     * @return List<TableOneUniqueKeyPO>
     */
    List<TableOneUniqueKeyPO> selectByExample(TableOneUniqueKeyPOExample example);

    /**
     * auto method:selectByExampleForUpdate
     * @param example
     * @return TableOneUniqueKeyPO
     */
    TableOneUniqueKeyPO selectByExampleForUpdate(TableOneUniqueKeyPOExample example);

    /**
     * auto method:selectByPrimaryKey
     * @param orgId
     * @param code
     * @return TableOneUniqueKeyPO
     */
    TableOneUniqueKeyPO selectByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:selectByPrimaryKeyForUpdate
     * @param orgId
     * @param code
     * @return TableOneUniqueKeyPO
     */
    TableOneUniqueKeyPO selectByPrimaryKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:updateByExampleSelective
     * @param record
     * @param example
     * @return int
     */
    int updateByExampleSelective(@Param("record") TableOneUniqueKeyPO record, @Param("example") TableOneUniqueKeyPOExample example);

    /**
     * auto method:updateByExample
     * @param record
     * @param example
     * @return int
     */
    int updateByExample(@Param("record") TableOneUniqueKeyPO record, @Param("example") TableOneUniqueKeyPOExample example);

    /**
     * auto method:updateByPrimaryKeySelective
     * @param record
     * @return int
     */
    int updateByPrimaryKeySelective(TableOneUniqueKeyPO record);

    /**
     * auto method:updateByPrimaryKey
     * @param record
     * @return int
     */
    int updateByPrimaryKey(TableOneUniqueKeyPO record);

    /**
     * auto method:selectByUniqueKey
     * @param orgId
     * @param code
     * @return TableOneUniqueKeyPO
     */
    TableOneUniqueKeyPO selectByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:selectByUniqueKeyForUpdate
     * @param orgId
     * @param code
     * @return TableOneUniqueKeyPO
     */
    TableOneUniqueKeyPO selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:updateByUniqueKeySelective
     * @param record
     * @return int
     */
    int updateByUniqueKeySelective(TableOneUniqueKeyPO record);

    /**
     * auto method:deleteByUniqueKey
     * @param orgId
     * @param code
     * @return int
     */
    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:batchInsert
     * @param list
     * @return int
     */
    int batchInsert(@Param("list") List<TableOneUniqueKeyPO> list);

    /**
     * auto method:upsertByPrimaryKey
     * @param record
     * @return int
     */
    int upsertByPrimaryKey(TableOneUniqueKeyPO record);

    /**
     * auto method:upsertByUniqueKey
     * @param record
     * @return int
     */
    int upsertByUniqueKey(TableOneUniqueKeyPO record);
}