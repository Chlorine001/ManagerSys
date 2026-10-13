package com.chlorine.water.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chlorine.water.entity.BackNotice;

import java.util.List;

/**
 * @author Chlorine
 * @description 针对表【back_notice】的数据库操作Mapper
 * @createDate 2024-04-23 18:11:50
 * @Entity com.chlorine.water.entity.BackNotice
 */
public interface BackNoticeMapper extends BaseMapper<BackNotice> {
    List<BackNotice> getBackNotice();
}




