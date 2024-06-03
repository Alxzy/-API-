package com.zy.project.service;

import com.zy.project.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2024-05-02 21:15:16
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b);
}
