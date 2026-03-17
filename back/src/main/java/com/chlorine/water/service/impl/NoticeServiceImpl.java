package com.chlorine.water.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chlorine.water.entity.Notice;
import com.chlorine.water.mapper.NoticeMapper;
import com.chlorine.water.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * @author Chlorine
 * @description 针对表【notice(公告)】的数据库操作Service实现
 * @createDate 2024-04-23 16:46:25
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
        implements NoticeService {

}




