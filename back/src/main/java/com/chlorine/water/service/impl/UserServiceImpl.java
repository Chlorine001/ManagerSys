package com.chlorine.water.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chlorine.water.common.exception.BusinessException;
import com.chlorine.water.entity.User;
import com.chlorine.water.mapper.UserMapper;
import com.chlorine.water.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.chlorine.water.common.exception.ErrorCodes.USER_ALREADY_FOUND;

/**
 * @author Chlorine
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-04-23 16:46:25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public User registerUser(User user) {
        // 1. 业务校验：用户名是否已存在
        Long exists = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getName, user.getName()));
        if (exists != null && exists > 0) {
            throw new BusinessException(USER_ALREADY_FOUND);
        }
        // 2. 密码加密（生产接入 BCrypt 时在此处 encode）
        user.setPassword(user.getPassword());
        // 3. 设置默认值
        user.setCreateTime(LocalDateTime.now());
        user.setTrueState(0);  // 非实名
        user.setRoleId(1);     // 默认普通用户（TODO：应默认「普通用户」角色 id，与 role 表对齐）
        user.setState(0);      // 非锁定
        // 4. 保存
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
        return userMapper.updateById(user) > 0;
    }
}
