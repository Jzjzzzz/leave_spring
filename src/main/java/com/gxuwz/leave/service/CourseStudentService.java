package com.gxuwz.leave.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Course;
import com.gxuwz.leave.entity.CourseStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxuwz.leave.entity.Student;
import com.gxuwz.leave.entity.query.CourseQuery;
import com.gxuwz.leave.entity.vo.RubyClassCourseVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 选课 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
public interface CourseStudentService extends IService<CourseStudent> {


    HashMap<String, Object> listPage(Page<Course> coursePage, CourseQuery courseQuery, Long teacherId);

    boolean curricula(Long courseId, Long studentId);

    List<RubyClassCourseVo> getRubyClassList(Long courseId);

    boolean removeByCourseId(Long courseId, Long rubyClassId);

    Page<Student> listPageStudent(Page<Student> studentPage, Long rubyClassId);

    ArrayList<Student>  randomRoll(Long rubyClassId);
}
