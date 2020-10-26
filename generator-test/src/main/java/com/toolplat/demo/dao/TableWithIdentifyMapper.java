package com.toolplat.demo.dao;

import com.toolplat.demo.domain.TableWithIdentifyPO;
import com.toolplat.demo.domain.TableWithIdentifyPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableWithIdentifyMapper {
    /**
     * auto method:countByExample
     * @param example
     * @return long
     */
    long countByExample(TableWithIdentifyPOExample example);

    /**
     * auto method:deleteByExample
     * @param example
     * @return int
     */
    int deleteByExample(TableWithIdentifyPOExample example);

    /**
     * auto method:deleteByPrimaryKey
     * @param id
     * @return int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * auto method:insert
     * @param record
     * @return int
     */
    int insert(TableWithIdentifyPO record);

    /**
     * auto method:insertSelective
     * @param record
     * @return int
     */
    int insertSelective(TableWithIdentifyPO record);

    /**
     * auto method:selectByExample
     * @param example
     * @return List<TableWithIdentifyPO>
     */
    List<TableWithIdentifyPO> selectByExample(TableWithIdentifyPOExample example);

    /**
     * auto method:selectByExampleForUpdate
     * @param example
     * @return TableWithIdentifyPO
     */
    TableWithIdentifyPO selectByExampleForUpdate(TableWithIdentifyPOExample example);

    /**
     * auto method:selectByPrimaryKey
     * @param id
     * @return TableWithIdentifyPO
     */
    TableWithIdentifyPO selectByPrimaryKey(Long id);

    /**
     * auto method:selectByPrimaryKeyForUpdate
     * @param orgId
     * @param code
     * @return TableWithIdentifyPO
     */
    TableWithIdentifyPO selectByPrimaryKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:updateByExampleSelective
     * @param record
     * @param example
     * @return int
     */
    int updateByExampleSelective(@Param("record") TableWithIdentifyPO record, @Param("example") TableWithIdentifyPOExample example);

    /**
     * auto method:updateByExample
     * @param record
     * @param example
     * @return int
     */
    int updateByExample(@Param("record") TableWithIdentifyPO record, @Param("example") TableWithIdentifyPOExample example);

    /**
     * auto method:updateByPrimaryKeySelective
     * @param record
     * @return int
     */
    int updateByPrimaryKeySelective(TableWithIdentifyPO record);

    /**
     * auto method:updateByPrimaryKey
     * @param record
     * @return int
     */
    int updateByPrimaryKey(TableWithIdentifyPO record);

    /**
     * auto method:selectByUniqueKey
     * @param orgId
     * @param code
     * @return TableWithIdentifyPO
     */
    TableWithIdentifyPO selectByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:selectByUniqueKeyForUpdate
     * @param orgId
     * @param code
     * @return TableWithIdentifyPO
     */
    TableWithIdentifyPO selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    /**
     * auto method:updateByUniqueKeySelective
     * @param record
     * @return int
     */
    int updateByUniqueKeySelective(TableWithIdentifyPO record);

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
    int batchInsert(@Param("list") List<TableWithIdentifyPO> list);

    /**
     * auto method:upsertByPrimaryKey
     * @param record
     * @return int
     */
    int upsertByPrimaryKey(TableWithIdentifyPO record);

    /**
     * auto method:upsertByUniqueKey
     * @param record
     * @return int
     */
    int upsertByUniqueKey(TableWithIdentifyPO record);
}