package com.gxuwz.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxuwz.leave.entity.query.StudentQuery;

import java.io.InputStream;
import java.util.HashMap;

/**
 * <p>
 * 学生 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-19
 */
public interface StudentService extends IService<Student> {

    HashMap<String,Object> listPage(Page<Student> studentPage, StudentQuery studentQuery);

    boolean saveStudent(Student student);

    boolean updateStudentById(Student student);

    boolean removeRubyClassById(Long id);

    void importData(InputStream inputStream);
}
