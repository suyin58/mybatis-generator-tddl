package com.dingtalk.demo.dao;

import com.dingtalk.demo.domain.TableTwoUniqueKey;
import com.dingtalk.demo.domain.TableTwoUniqueKeyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableTwoUniqueKeyMapper {
    long countByExample(TableTwoUniqueKeyCriteria example);

    int deleteByExample(TableTwoUniqueKeyCriteria example);

    int deleteByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int insert(TableTwoUniqueKey record);

    int insertSelective(TableTwoUniqueKey record);

    List<TableTwoUniqueKey> selectByExample(TableTwoUniqueKeyCriteria example);

    TableTwoUniqueKey selectByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByExampleSelective(@Param("record") TableTwoUniqueKey record, @Param("example") TableTwoUniqueKeyCriteria example);

    int updateByExample(@Param("record") TableTwoUniqueKey record, @Param("example") TableTwoUniqueKeyCriteria example);

    int updateByPrimaryKeySelective(TableTwoUniqueKey record);

    int updateByPrimaryKey(TableTwoUniqueKey record);

    TableTwoUniqueKey selectByUniqueKey(@Param("orgId") Long orgId, @Param("cid") String cid);

    int updateByUniqueKeySelective(TableTwoUniqueKey record);

    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("cid") String cid);
}