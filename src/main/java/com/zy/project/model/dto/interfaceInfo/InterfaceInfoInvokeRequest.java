package com.zy.project.model.dto.interfaceInfo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;


/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {



    /**
     * id
     */
    private long id;


    /**
     * 请求参数
     */
    private String requestParams;


    private static final long serialVersionUID = 1L;
}