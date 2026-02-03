package com.chlorine.water.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chlorine.water.entity.LoginInfo;
import com.chlorine.water.service.LoginInfoService;
import com.chlorine.water.mapper.LoginInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Chlorine
* @description 针对表【login_info(登录信息)】的数据库操作Service实现
* @createDate 2024-04-23 16:46:26
*/
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo>
    implements LoginInfoService{

}




