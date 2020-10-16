package com.dingtalk.demo.dao;

import com.dingtalk.demo.MapperBaseTest;
import com.dingtalk.demo.domain.TableTwoUniqueKey;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * DDL:
 CREATE TABLE `table_two_unique_key` (
 `id` bigint unsigned NOT NULL COMMENT 'id',
 `gmt_create` datetime NOT NULL COMMENT '创建时间',
 `gmt_modified` datetime NOT NULL COMMENT '修改时间',
 `org_id` bigint unsigned NOT NULL COMMENT '多群主键',
 `cid` varchar(32) NOT NULL COMMENT '会话ID',
 `code` varchar(32) NOT NULL COMMENT '多码',
 PRIMARY KEY (`org_id`,`code`),
 UNIQUE KEY `uk_org_cid` (`org_id`,`cid`),
 UNIQUE KEY `uk_org_code` (`org_id`,`code`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='多个uk测试';
 */
public class TableTwoUniqueKeyMapperTest extends MapperBaseTest {


    @Resource
    TableTwoUniqueKeyMapper tableTwoUniqueKeyMapper;

    static TableTwoUniqueKey tableTwoUniqueKey =
            TableTwoUniqueKey.builder().id(1L).cid("cid").code("code").gmtCreate(new Date()).gmtModified(new Date()).orgId(1L).build();

    @Test
    public void testInsert(){
        tableTwoUniqueKeyMapper.insertSelective(tableTwoUniqueKey);
    }


    @Test
    public void testSelectByUniqueKey(){

        TableTwoUniqueKey mapping = tableTwoUniqueKeyMapper.selectByUniqueKey(tableTwoUniqueKey.getOrgId(),
                tableTwoUniqueKey.getCid());
        Assert.assertNotNull(mapping);
    }
}
