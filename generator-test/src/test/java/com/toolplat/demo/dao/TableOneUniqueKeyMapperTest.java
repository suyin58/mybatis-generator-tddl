package com.toolplat.demo.dao;

import com.toolplat.demo.MapperBaseTest;
import com.toolplat.demo.domain.TableOneUniqueKeyPO;
import com.toolplat.demo.domain.TableOneUniqueKeyPOExample;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DDL:
 CREATE TABLE `table_one_unique_key` (
 `id` bigint unsigned NOT NULL COMMENT 'id',
 `gmt_create` datetime NOT NULL COMMENT '创建时间',
 `gmt_modified` datetime NOT NULL COMMENT '修改时间',
 `org_id` bigint unsigned NOT NULL COMMENT '多群主键',
 `cid` varchar(32) NOT NULL COMMENT '会话ID',
 `code` varchar(32) NOT NULL COMMENT '多码',
 PRIMARY KEY (`org_id`,`code`),
 UNIQUE KEY `uk_org_code` (`org_id`,`code`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='单个uk测试';
 */
public class TableOneUniqueKeyMapperTest extends MapperBaseTest {


    @Resource
    TableOneUniqueKeyMapper tableOneUniqueKeyMapper;

    static TableOneUniqueKeyPO tableOneUniqueKey =
            TableOneUniqueKeyPO.builder().id(1L).cid("cid").code("code").gmtCreate(new Date()).gmtModified(new Date()).orgId(1L).build();


    List<TableOneUniqueKeyPO> batchs = new ArrayList<>();

    int batchNum = 10;
    @Before
    public void init() {
        for (int i = 5; i < 5 + batchNum; i++) {
            batchs.add(TableOneUniqueKeyPO.builder()
                    .id(Long.valueOf(i))
                    .cid("cid" + i)
                    .code("code" + i)
                    .gmtCreate(new Date())
                    .gmtModified(new Date())
                    .orgId(1L + i)
                    .build()
            );
        }
    }


    @Test
    public void testInsert(){

        int n = tableOneUniqueKeyMapper.deleteByUniqueKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        tableOneUniqueKeyMapper.insertSelective(tableOneUniqueKey);
    }


    @Test
    public void testSelectByUniqueKey(){

        TableOneUniqueKeyPO mapping = tableOneUniqueKeyMapper.selectByUniqueKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertNotNull(mapping);
    }
    @Test
    public void testSelectByPrimaryKeyForUpdate(){

        int n = tableOneUniqueKeyMapper.deleteByPrimaryKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        tableOneUniqueKeyMapper.insertSelective(tableOneUniqueKey);

        TableOneUniqueKeyPO mapping = tableOneUniqueKeyMapper.selectByPrimaryKeyForUpdate(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertNotNull(mapping);
    }

    @Test
    public void testUpdateByUnikeyKey(){
        Long updateId = 10L;
        TableOneUniqueKeyPO mapping = tableOneUniqueKeyMapper.selectByUniqueKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        mapping.setId(updateId);
        tableOneUniqueKeyMapper.updateByUniqueKeySelective(mapping);
        TableOneUniqueKeyPO mapping1 = tableOneUniqueKeyMapper.selectByUniqueKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertTrue(updateId.equals(mapping1.getId()));
    }


    @Test
    public void testDeleteByUnikeyKey(){
        Long updateId = 10L;
        int n = tableOneUniqueKeyMapper.deleteByUniqueKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        TableOneUniqueKeyPO mapping = tableOneUniqueKeyMapper.selectByUniqueKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertNull(mapping);
    }


    @Test
    public void testBatchInsert() {
        tableOneUniqueKeyMapper.deleteByExample(new TableOneUniqueKeyPOExample());
        int n = tableOneUniqueKeyMapper.batchInsert(batchs);
        Assert.assertTrue(batchNum == n);
    }

    @Test
    public void testLimit(){
        int limit = 2;
        TableOneUniqueKeyPOExample crt = new TableOneUniqueKeyPOExample();
        crt.setStart(0);
        crt.setLimit(limit);
        crt.createCriteria().andIdGreaterThan(2L);
        List<TableOneUniqueKeyPO>  list =  tableOneUniqueKeyMapper.selectByExample(crt);
        Assert.assertTrue(limit == list.size());
    }

    @Test
    public void testUpSertByUniqueKeyNotExist(){
        tableOneUniqueKeyMapper.deleteByUniqueKey(tableOneUniqueKey.getOrgId(), tableOneUniqueKey.getCode());
        tableOneUniqueKeyMapper.upsertByUniqueKey(tableOneUniqueKey);
        TableOneUniqueKeyPO res = tableOneUniqueKeyMapper.selectByUniqueKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertTrue(tableOneUniqueKey.getCid().equals(res.getCid()));

    }
    @Test
    public void testUpSertByUniqueKeyExist(){

        String upVal = "UpCid";
        tableOneUniqueKeyMapper.deleteByUniqueKey(tableOneUniqueKey.getOrgId(), tableOneUniqueKey.getCode());
        tableOneUniqueKeyMapper.upsertByUniqueKey(tableOneUniqueKey);
        TableOneUniqueKeyPO resInsert = tableOneUniqueKeyMapper.selectByUniqueKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertTrue(tableOneUniqueKey.getCid().equals(resInsert.getCid()));
        tableOneUniqueKey.setCid(upVal);
        tableOneUniqueKeyMapper.upsertByUniqueKey(tableOneUniqueKey);
        TableOneUniqueKeyPO resUpdate = tableOneUniqueKeyMapper.selectByUniqueKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertTrue(upVal.equals(resUpdate.getCid()));

    }

    @Test
    public void testUpSertByPrimaryKeyNotExist(){
        tableOneUniqueKeyMapper.deleteByPrimaryKey(tableOneUniqueKey.getOrgId(), tableOneUniqueKey.getCode());
        tableOneUniqueKeyMapper.upsertByPrimaryKey(tableOneUniqueKey);
        TableOneUniqueKeyPO res = tableOneUniqueKeyMapper.selectByPrimaryKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertTrue(tableOneUniqueKey.getCid().equals(res.getCid()));

    }
    @Test
    public void testUpSertByPrimaryKeyExist(){

        String upVal = "UpCid";
        tableOneUniqueKeyMapper.deleteByPrimaryKey(tableOneUniqueKey.getOrgId(), tableOneUniqueKey.getCode());
        tableOneUniqueKeyMapper.upsertByPrimaryKey(tableOneUniqueKey);
        TableOneUniqueKeyPO resInsert = tableOneUniqueKeyMapper.selectByPrimaryKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertTrue(tableOneUniqueKey.getCid().equals(resInsert.getCid()));
        tableOneUniqueKey.setCid(upVal);
        tableOneUniqueKeyMapper.upsertByPrimaryKey(tableOneUniqueKey);
        TableOneUniqueKeyPO resUpdate = tableOneUniqueKeyMapper.selectByPrimaryKey(tableOneUniqueKey.getOrgId(),
                tableOneUniqueKey.getCode());
        Assert.assertTrue(upVal.equals(resUpdate.getCid()));

    }
}
