package com.chlorine.water.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chlorine.water.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Chlorine
 * @description 针对表【menu(菜单)】的数据库操作Mapper
 * @createDate 2024-04-23 16:46:26
 * @Entity com.chlorine.water.entity.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {

    //fixme 需要调整
    @Select("SELECT m.* " +
            "FROM menu m " +
            "JOIN role_menu rm ON rm.menu_id = m.id " +
            "JOIN role r ON r.id = rm.role_id " +
            "JOIN user u ON u.role = r.numb " +
            "WHERE u.name = #{username} " +
            "ORDER BY m.pid, m.id")
    List<Menu> selectMenusByUsername(@Param("username") String username);

    /** 统计某个父级下的子节点数量 */
    @Select("SELECT COUNT(*) FROM menu WHERE pid = #{pid}")
    Long countByPid(@Param("pid") Integer pid);

    /** 同级下相同路径的数量（排除 excludeId） */
    @Select("<script>" +
            "SELECT COUNT(*) FROM menu " +
            "WHERE pid = #{pid} AND url = #{url} " +
            "<if test='excludeId != null'> AND id != #{excludeId} </if>" +
            "</script>")
    Long countByPidAndUrl(@Param("pid") Integer pid,
                          @Param("url") String url,
                          @Param("excludeId") Integer excludeId);
}


