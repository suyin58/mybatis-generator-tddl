package com.dingtalk.demo.dao;

import com.dingtalk.demo.domain.TableOneUniqueKey;
import com.dingtalk.demo.domain.TableOneUniqueKeyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableOneUniqueKeyMapper {
    long countByExample(TableOneUniqueKeyCriteria example);

    int deleteByExample(TableOneUniqueKeyCriteria example);

    int deleteByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int insert(TableOneUniqueKey record);

    int insertSelective(TableOneUniqueKey record);

    List<TableOneUniqueKey> selectByExample(TableOneUniqueKeyCriteria example);

    TableOneUniqueKey selectByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByExampleSelective(@Param("record") TableOneUniqueKey record, @Param("example") TableOneUniqueKeyCriteria example);

    int updateByExample(@Param("record") TableOneUniqueKey record, @Param("example") TableOneUniqueKeyCriteria example);

    int updateByPrimaryKeySelective(TableOneUniqueKey record);

    int updateByPrimaryKey(TableOneUniqueKey record);

    TableOneUniqueKey selectByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByUniqueKeySelective(TableOneUniqueKey record);

    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);
}