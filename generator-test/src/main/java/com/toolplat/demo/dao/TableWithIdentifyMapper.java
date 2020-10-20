package com.toolplat.demo.dao;

import com.toolplat.demo.domain.TableWithIdentifyPO;
import com.toolplat.demo.domain.TableWithIdentifyPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableWithIdentifyMapper {
    long countByExample(TableWithIdentifyPOExample example);

    int deleteByExample(TableWithIdentifyPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TableWithIdentifyPO record);

    int insertSelective(TableWithIdentifyPO record);

    List<TableWithIdentifyPO> selectByExample(TableWithIdentifyPOExample example);

    TableWithIdentifyPO selectByExampleForUpdate(TableWithIdentifyPOExample example);

    TableWithIdentifyPO selectByPrimaryKey(Long id);

    TableWithIdentifyPO selectByPrimaryKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByExampleSelective(@Param("record") TableWithIdentifyPO record, @Param("example") TableWithIdentifyPOExample example);

    int updateByExample(@Param("record") TableWithIdentifyPO record, @Param("example") TableWithIdentifyPOExample example);

    int updateByPrimaryKeySelective(TableWithIdentifyPO record);

    int updateByPrimaryKey(TableWithIdentifyPO record);

    TableWithIdentifyPO selectByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    TableWithIdentifyPO selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByUniqueKeySelective(TableWithIdentifyPO record);

    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    int batchInsert(@Param("list") List<TableWithIdentifyPO> list);

    int upsertByPrimaryKey(TableWithIdentifyPO record);

    int upsertByUniqueKey(TableWithIdentifyPO record);
}