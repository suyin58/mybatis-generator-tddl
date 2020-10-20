package com.toolplat.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 单个uk测试
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TableOneUniqueKeyPO implements Serializable {
    private static final long serialVersionUID = 71731146100952L;

    /**
     * 多群主键
     */
    private Long orgId;

    /**
     * 多码
     */
    private String code;

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
     * 会话ID
     */
    private String cid;
}