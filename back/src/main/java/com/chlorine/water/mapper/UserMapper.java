package com.chlorine.water.mapper;

import com.chlorine.water.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * @author Chlorine
 * @description 针对表【user(用户表)】的数据库操作Mapper
 * @createDate 2024-04-23 16:46:25
 * @Entity com.chlorine.water.entity.User
 */
public interface UserMapper extends BaseMapper<User> {

    @Insert("INSERT INTO user(name, password, create_time) VALUES(#{name}, #{password}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Long id);

    @Select("SELECT * FROM user WHERE name = #{name}")
    User selectByName(String name);

    @Update("UPDATE user SET email = #{email} WHERE id = #{id}")
    boolean updateUserEmail(User user);


}




