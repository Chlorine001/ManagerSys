package com.chlorine.water.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chlorine.water.entity.Student;
import com.chlorine.water.mapper.StudentMapper;
import com.chlorine.water.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @author Chlorine
 * @description 针对表【student(学生班级表)】的数据库操作Service实现
 * @createDate 2024-04-23 16:46:25
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

}




