package com.chlorine.water.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chlorine.water.entity.User;

/**
 * @author Chlorine
 * @description 针对表【user(用户表)】的数据库操作Mapper
 * @createDate 2024-04-23 16:46:25
 * @Entity com.chlorine.water.entity.User
 *
 * 说明：继承 BaseMapper 即可获得全部单表 CRUD，不要覆盖 insert/selectById，
 * 否则会导致部分字段被静默丢弃、方法签名冲突。
 * 自定义查询走 selectByName（XML）或 LambdaQueryWrapper。
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户（走 XML，保证列名映射正确）
     */
    User selectByName(String name);
}
