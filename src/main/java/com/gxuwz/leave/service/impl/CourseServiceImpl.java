package com.gxuwz.leave.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Course;
import com.gxuwz.leave.entity.RubyClass;
import com.gxuwz.leave.entity.Student;
import com.gxuwz.leave.entity.Teacher;
import com.gxuwz.leave.entity.query.CourseQuery;
import com.gxuwz.leave.entity.vo.CourseVo;
import com.gxuwz.leave.entity.vo.StudentVo;
import com.gxuwz.leave.mapper.CourseMapper;
import com.gxuwz.leave.mapper.TeacherMapper;
import com.gxuwz.leave.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public HashMap<String, Object> listPage(Page<Course> coursePage, CourseQuery courseQuery) {
        ArrayList<CourseVo> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseQuery)){
            if(!StringUtils.isEmpty(courseQuery.getName())){
                wrapper.like("name",courseQuery.getName());
            }
        }
        baseMapper.selectPage(coursePage,wrapper);
        List<Course> records = coursePage.getRecords();
        CourseVo courseVo;
        for (Course item : records) {
            //封装数据
            courseVo = baseMapper.getBaseCourseList(item.getId());
            list.add(courseVo);
        }
        //查询班级列表
        List<Teacher> teacherList = teacherMapper.selectList(null);
        map.put("teacherList",teacherList);
        map.put("list",list);
        map.put("total",coursePage.getTotal());
        return map;
    }

    @Override
    public boolean saveCourse(Course course) {
        course.setGmtModified(new Date());
        course.setGmtCreate(new Date());
        course.setStatus(1);
        course.setCount(0);
        if(baseMapper.insert(course)>0) return true;
        return false;
    }

    @Override
    public boolean updateCourseById(Course course) {
        course.setGmtModified(new Date());
        if(baseMapper.updateById(course)>0) return true;
        return false;
    }

    @Override
    public List<Course> getByTeacherId(Long teacherId) {
        List<Course> list = baseMapper.selectList(new QueryWrapper<Course>().eq("teacher_id", teacherId));
        return list;
    }
}
