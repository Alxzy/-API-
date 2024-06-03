package com.zy.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.project.model.entity.InterfaceInfo;
import com.zy.project.service.InterfaceInfoService;
import com.zy.project.mapper.InterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
* @createDate 2024-05-02 21:15:16
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b) {
        // todo 接口合法校验
    }
}




