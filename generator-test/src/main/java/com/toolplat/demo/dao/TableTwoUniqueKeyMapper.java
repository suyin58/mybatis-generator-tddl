package com.toolplat.demo.dao;

import com.toolplat.demo.domain.TableTwoUniqueKeyPO;
import com.toolplat.demo.domain.TableTwoUniqueKeyPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableTwoUniqueKeyMapper {
    long countByExample(TableTwoUniqueKeyPOExample example);

    int deleteByExample(TableTwoUniqueKeyPOExample example);

    int deleteByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int insert(TableTwoUniqueKeyPO record);

    int insertSelective(TableTwoUniqueKeyPO record);

    List<TableTwoUniqueKeyPO> selectByExample(TableTwoUniqueKeyPOExample example);

    TableTwoUniqueKeyPO selectByExampleForUpdate(TableTwoUniqueKeyPOExample example);

    TableTwoUniqueKeyPO selectByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByExampleSelective(@Param("record") TableTwoUniqueKeyPO record, @Param("example") TableTwoUniqueKeyPOExample example);

    int updateByExample(@Param("record") TableTwoUniqueKeyPO record, @Param("example") TableTwoUniqueKeyPOExample example);

    int updateByPrimaryKeySelective(TableTwoUniqueKeyPO record);

    int updateByPrimaryKey(TableTwoUniqueKeyPO record);

    TableTwoUniqueKeyPO selectByUniqueKey(@Param("orgId") Long orgId, @Param("cid") String cid);

    TableTwoUniqueKeyPO selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("cid") String cid);

    int updateByUniqueKeySelective(TableTwoUniqueKeyPO record);

    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("cid") String cid);

    int upsertByUniqueKey(TableTwoUniqueKeyPO record);

    int batchInsert(@Param("list") List<TableTwoUniqueKeyPO> list);
}