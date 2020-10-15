package com.dingtalk.demo.dao;

import com.dingtalk.demo.MapperBaseTest;
import com.dingtalk.demo.domain.TableOneUniqueKey;
import com.dingtalk.demo.domain.TableWithIdentify;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CREATE TABLE `table_with_identify` (
 * `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
 * `gmt_create` datetime NOT NULL COMMENT '创建时间',
 * `gmt_modified` datetime NOT NULL COMMENT '修改时间',
 * `org_id` bigint unsigned NOT NULL COMMENT '多群主键',
 * `cid` varchar(32) NOT NULL COMMENT '会话ID',
 * `code` varchar(32) NOT NULL COMMENT '多码',
 * PRIMARY KEY (`id`),
 * UNIQUE KEY `uk_org_code` (`org_id`,`code`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='自增长测试';
 */
public class TableWithIdentifyMapperTest extends MapperBaseTest {

    @Resource
    TableWithIdentifyMapper tableWithIdentifyMapper;

    List<TableWithIdentify> columns = new ArrayList<>();

    @Before
    public void init() {
        for (int i = 0; i < 10; i++) {
            columns.add(TableWithIdentify.builder()
                    .cid("cid" + i)
                    .code("code" + i).gmtCreate(new Date()).gmtModified(new Date()).orgId(1L + i)
                    .build()
            );
        }
    }

    @Test
    public void testBatchInsert() {
        int n = tableWithIdentifyMapper.batchInsert(columns);
        System.out.println(n);
    }
}
