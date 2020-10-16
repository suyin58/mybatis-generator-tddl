package com.dingtalk.demo.dao;

import com.dingtalk.demo.domain.TableWithIdentify;
import com.dingtalk.demo.domain.TableWithIdentifyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableWithIdentifyMapper {
    long countByExample(TableWithIdentifyCriteria example);

    int deleteByExample(TableWithIdentifyCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(TableWithIdentify record);

    int insertSelective(TableWithIdentify record);

    List<TableWithIdentify> selectByExample(TableWithIdentifyCriteria example);

    TableWithIdentify selectByExampleForUpdate(TableWithIdentifyCriteria example);

    TableWithIdentify selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TableWithIdentify record, @Param("example") TableWithIdentifyCriteria example);

    int updateByExample(@Param("record") TableWithIdentify record, @Param("example") TableWithIdentifyCriteria example);

    int updateByPrimaryKeySelective(TableWithIdentify record);

    int updateByPrimaryKey(TableWithIdentify record);

    TableWithIdentify selectByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    TableWithIdentify selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByUniqueKeySelective(TableWithIdentify record);

    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    int batchInsert(@Param("list") List<TableWithIdentify> list);

    int upsertByUniqueKey(TableWithIdentify record);
}