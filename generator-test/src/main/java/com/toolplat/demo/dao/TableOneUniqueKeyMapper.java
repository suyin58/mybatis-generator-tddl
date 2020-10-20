package com.toolplat.demo.dao;

import com.toolplat.demo.domain.TableOneUniqueKeyPO;
import com.toolplat.demo.domain.TableOneUniqueKeyPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableOneUniqueKeyMapper {
    long countByExample(TableOneUniqueKeyPOExample example);

    int deleteByExample(TableOneUniqueKeyPOExample example);

    int deleteByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int insert(TableOneUniqueKeyPO record);

    int insertSelective(TableOneUniqueKeyPO record);

    List<TableOneUniqueKeyPO> selectByExample(TableOneUniqueKeyPOExample example);

    TableOneUniqueKeyPO selectByExampleForUpdate(TableOneUniqueKeyPOExample example);

    TableOneUniqueKeyPO selectByPrimaryKey(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByExampleSelective(@Param("record") TableOneUniqueKeyPO record, @Param("example") TableOneUniqueKeyPOExample example);

    int updateByExample(@Param("record") TableOneUniqueKeyPO record, @Param("example") TableOneUniqueKeyPOExample example);

    int updateByPrimaryKeySelective(TableOneUniqueKeyPO record);

    int updateByPrimaryKey(TableOneUniqueKeyPO record);

    TableOneUniqueKeyPO selectByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    TableOneUniqueKeyPO selectByUniqueKeyForUpdate(@Param("orgId") Long orgId, @Param("code") String code);

    int updateByUniqueKeySelective(TableOneUniqueKeyPO record);

    int deleteByUniqueKey(@Param("orgId") Long orgId, @Param("code") String code);

    int upsertByUniqueKey(TableOneUniqueKeyPO record);

    int batchInsert(@Param("list") List<TableOneUniqueKeyPO> list);
}