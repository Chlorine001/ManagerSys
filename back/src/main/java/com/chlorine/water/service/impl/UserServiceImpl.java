package com.chlorine.water.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chlorine.water.entity.User;
import com.chlorine.water.service.UserService;
import com.chlorine.water.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Chlorine
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-04-23 16:46:25
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




