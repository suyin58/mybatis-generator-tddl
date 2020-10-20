package com.toolplat.demo.dao;

import com.toolplat.demo.domain.TableTwoUniqueKey;
import com.toolplat.demo.domain.TableTwoUniqueKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableTwoUniqueKeyMapper {
    long countByExample(TableTwoUniqueKeyExample example);

    int deleteByExample(TableTwoUniqueKeyExample example);

    int deleteByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int insert(TableTwoUniqueKey record);

    int insertSelective(TableTwoUniqueKey record);

    List<TableTwoUniqueKey> selectByExample(TableTwoUniqueKeyExample example);

    TableTwoUniqueKey selectByExampleForUpdate(TableTwoUniqueKeyExample example);

    TableTwoUniqueKey selectByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByExampleSelective(@Param("record") TableTwoUniqueKey record, @Param("example") TableTwoUniqueKeyExample example);

    int updateByExample(@Param("record") TableTwoUniqueKey record, @Param("example") TableTwoUniqueKeyExample example);

    int updateByPrimaryKeySelective(TableTwoUniqueKey record);

    int updateByPrimaryKey(TableTwoUniqueKey record);

    TableTwoUniqueKey selectByUniqueKey(@Param("orgId") Long orgId, @Param("cid") String cid);

    TableTwoUniqueKey selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("cid") String cid);

    int updateByUniqueKeySelective(TableTwoUniqueKey record);

    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("cid") String cid);

    int upsertByUniqueKey(TableTwoUniqueKey record);

    int batchInsert(@Param("list") List<TableTwoUniqueKey> list);
}