package com.chlorine.water.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chlorine.water.entity.User;
import com.chlorine.water.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService_pass extends ServiceImpl<UserMapper, User> {
    public boolean saveUser(User user) {
//        if (user.getId() == null) {
//            return save(user);
//        }else {
//            return updateById(user);
//        }

        return saveOrUpdate(user);

    }

//    @Autowired
//    private UserMapper userMapper;

//    public int save(User user) {
//        if (user.getId() == null) {
//            return userMapper.insert(user);
//        } else {
//            return userMapper.update(user);
//        }
//    }
}
