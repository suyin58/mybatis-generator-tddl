package com.toolplat.demo.dao;

import com.toolplat.demo.domain.TableOneUniqueKey;
import com.toolplat.demo.domain.TableOneUniqueKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableOneUniqueKeyMapper {
    long countByExample(TableOneUniqueKeyExample example);

    int deleteByExample(TableOneUniqueKeyExample example);

    int deleteByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int insert(TableOneUniqueKey record);

    int insertSelective(TableOneUniqueKey record);

    List<TableOneUniqueKey> selectByExample(TableOneUniqueKeyExample example);

    TableOneUniqueKey selectByExampleForUpdate(TableOneUniqueKeyExample example);

    TableOneUniqueKey selectByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByExampleSelective(@Param("record") TableOneUniqueKey record, @Param("example") TableOneUniqueKeyExample example);

    int updateByExample(@Param("record") TableOneUniqueKey record, @Param("example") TableOneUniqueKeyExample example);

    int updateByPrimaryKeySelective(TableOneUniqueKey record);

    int updateByPrimaryKey(TableOneUniqueKey record);

    TableOneUniqueKey selectByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    TableOneUniqueKey selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByUniqueKeySelective(TableOneUniqueKey record);

    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    int upsertByUniqueKey(TableOneUniqueKey record);

    int batchInsert(@Param("list") List<TableOneUniqueKey> list);
}