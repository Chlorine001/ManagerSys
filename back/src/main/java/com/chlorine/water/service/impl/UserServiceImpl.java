package com.chlorine.water.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chlorine.water.config.Error.BusinessException;
import com.chlorine.water.entity.User;
import com.chlorine.water.mapper.UserMapper;
import com.chlorine.water.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author Chlorine
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-04-23 16:46:25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

//    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public User registerUser(User user) {
        // 1. 业务校验（如用户名是否已存在）
        if (userMapper.selectByName(user.getName()) != null) {
            throw new BusinessException(1002);
        }
        user.setName(user.getName());
        // 2. 密码加密
        user.setPassword(user.getPassword());
//        user.setPassword(encodePassword(user.getPassword()));
        // 3. 设置创建时间
        user.setCreateTime(LocalDateTime.now());
        user.setTrueState(0);//非实名
        user.setRole(2);//默认学生
        user.setState(0);//非锁定账户
        // 4. 调用Mapper保存
        userMapper.insert(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByName(username);
    }

    @Override
    @Transactional
    public boolean updateUserEmail(Long id, String email) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return false;
        }
        user.setEmail(email);
        return userMapper.updateUserEmail(user);
    }

    /**
     * 密码加密方法
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
//    private String encodePassword(String rawPassword) {
//        return passwordEncoder.encode(rawPassword);
//    }

}




