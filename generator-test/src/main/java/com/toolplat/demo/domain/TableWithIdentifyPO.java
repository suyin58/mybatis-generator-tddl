package com.toolplat.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 自增长测试
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TableWithIdentifyPO implements Serializable {
    private static final long serialVersionUID = 8949803865561L;

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
}