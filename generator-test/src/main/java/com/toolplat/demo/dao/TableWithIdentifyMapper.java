package com.toolplat.demo.dao;

import com.toolplat.demo.domain.TableWithIdentify;
import com.toolplat.demo.domain.TableWithIdentifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableWithIdentifyMapper {
    long countByExample(TableWithIdentifyExample example);

    int deleteByExample(TableWithIdentifyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TableWithIdentify record);

    int insertSelective(TableWithIdentify record);

    List<TableWithIdentify> selectByExample(TableWithIdentifyExample example);

    TableWithIdentify selectByExampleForUpdate(TableWithIdentifyExample example);

    TableWithIdentify selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TableWithIdentify record, @Param("example") TableWithIdentifyExample example);

    int updateByExample(@Param("record") TableWithIdentify record, @Param("example") TableWithIdentifyExample example);

    int updateByPrimaryKeySelective(TableWithIdentify record);

    int updateByPrimaryKey(TableWithIdentify record);

    TableWithIdentify selectByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    TableWithIdentify selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByUniqueKeySelective(TableWithIdentify record);

    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    int upsertByUniqueKey(TableWithIdentify record);

    int batchInsert(@Param("list") List<TableWithIdentify> list);
}