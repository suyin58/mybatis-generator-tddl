package com.dingtalk.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 自增长测试
 * @author null
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TableWithIdentify implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 多群主键
     */
    private Long orgId;

    /**
     * 会话ID
     */
    private String cid;

    /**
     * 多码
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_with_identify
     *
     * @mbg.generated Tue Oct 20 16:37:06 CST 2020
     */
    private static final long serialVersionUID = 1L;
}