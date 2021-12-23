package com.gxuwz.leave.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxuwz.leave.entity.query.CourseQuery;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
public interface CourseService extends IService<Course> {

    HashMap<String, Object> listPage(Page<Course> coursePage, CourseQuery courseQuery);

    boolean saveCourse(Course course);

    boolean updateCourseById(Course course);

    List<Course> getByTeacherId(Long teacherId);
}
