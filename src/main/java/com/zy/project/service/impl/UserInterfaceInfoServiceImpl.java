package com.zy.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.project.common.ErrorCode;
import com.zy.project.exception.BusinessException;
import com.zy.project.model.entity.UserInterfaceInfo;
import com.zy.project.service.UserInterfaceInfoService;
import com.zy.project.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2024-06-03 21:33:06
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b) {

        if(userInterfaceInfo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时所有参数必须非空
        if(b){
            if(userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户不存在");
            }
        }

        if(userInterfaceInfo.getLeftNum() < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"剩余次数不能小于 0");
        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        //判断
        if(interfaceInfoId <= 0 || userId <= 0){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userId",userId);
        updateWrapper.gt("leftNum",0);
        // todo 事务管理 分布式锁 or sychronized(单机)
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        boolean update = this.update(updateWrapper);
        return update;
    }

}




