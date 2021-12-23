package com.gxuwz.leave.mapper;

import com.gxuwz.leave.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxuwz.leave.entity.vo.CourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
public interface CourseMapper extends BaseMapper<Course> {

    CourseVo getBaseCourseList(Long id);
}
