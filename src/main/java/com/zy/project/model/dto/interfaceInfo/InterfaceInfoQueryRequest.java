package com.zy.project.model.dto.interfaceInfo;

import com.zy.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author zy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

    private int[] idList;

    /**
     * 用户名
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    private Integer isDeleted;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 创建用户id
     */
    private Long userId;

    /**
     * 接口状态（0 - 关闭，1 - 开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    private static final long serialVersionUID = 1L;
}