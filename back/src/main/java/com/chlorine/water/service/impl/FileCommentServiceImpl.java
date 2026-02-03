package com.chlorine.water.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chlorine.water.entity.FileComment;
import com.chlorine.water.service.FileCommentService;
import com.chlorine.water.mapper.FileCommentMapper;
import org.springframework.stereotype.Service;

/**
* @author Chlorine
* @description 针对表【file_comment(文件评论)】的数据库操作Service实现
* @createDate 2024-04-23 16:46:26
*/
@Service
public class FileCommentServiceImpl extends ServiceImpl<FileCommentMapper, FileComment>
    implements FileCommentService{

}




