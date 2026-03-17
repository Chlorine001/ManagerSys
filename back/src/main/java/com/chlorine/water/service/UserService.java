package com.chlorine.water.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chlorine.water.entity.User;

/**
 * @author Chlorine
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2024-04-23 16:46:25
 */
public interface UserService extends IService<User> {

    User registerUser(User user);           // 用户注册（可能包含密码加密、校验等）

    User getUserById(Long id);               // 根据ID查询用户

    User getUserByUsername(String username); // 根据用户名查询用户

    boolean updateUserEmail(Long id, String email); // 更新邮箱

}
