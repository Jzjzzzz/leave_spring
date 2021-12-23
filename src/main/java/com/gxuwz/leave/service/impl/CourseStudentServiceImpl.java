package com.gxuwz.leave.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.*;
import com.gxuwz.leave.entity.query.CourseQuery;
import com.gxuwz.leave.entity.vo.RubyClassCourseVo;
import com.gxuwz.leave.mapper.CourseMapper;
import com.gxuwz.leave.mapper.CourseStudentMapper;
import com.gxuwz.leave.mapper.RubyClassMapper;
import com.gxuwz.leave.mapper.StudentMapper;
import com.gxuwz.leave.service.CourseStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 选课 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
@Service
public class CourseStudentServiceImpl extends ServiceImpl<CourseStudentMapper, CourseStudent> implements CourseStudentService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RubyClassMapper rubyClassMapper;

    @Override
    public HashMap<String, Object> listPage(Page<Course> coursePage, CourseQuery courseQuery, Long teacherId) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.eq("teacher_id",teacherId); //只查询该教师下的课程
        Page<Course> courseList; //课程列表
        //学生列表
        if (courseQuery == null){
            //封装课程列表
            courseList = courseMapper.selectPage(coursePage, courseWrapper);
            map.put("courseList",courseList);
            return map;
        }
        String name = courseQuery.getName();
        courseWrapper.like(StringUtils.isNotBlank(name), "name", name);
        courseList = courseMapper.selectPage(coursePage,courseWrapper);
        map.put("courseList",courseList);
        return map;
    }

    /**
     * 选课
     * @param courseId 课程id
     * @param rubyClassId 班级id
     * @return
     */
    @Transactional
    @Override
    public boolean curricula(Long courseId, Long rubyClassId) {

        Course course = courseMapper.selectById(courseId); //查询课程实体
        course.setCount(course.getCount()+1);//选课班级+1
        courseMapper.updateById(course);

        //封装选课信息
        CourseStudent model = new CourseStudent();
        model.setCourseId(courseId);
        model.setRubyClassId(rubyClassId);
        model.setGmtCreate(new Date());
        model.setGmtModified(new Date());
        model.setStatus(1);
        if(baseMapper.insert(model)>0) return true;
        return false;
    }

    @Override
    public List<RubyClassCourseVo> getRubyClassList(Long courseId) {
        ArrayList<RubyClassCourseVo> voList = new ArrayList<>();
        List<RubyClass> classList = rubyClassMapper.selectList(null);
        for (RubyClass model : classList) {
            RubyClassCourseVo vo = new RubyClassCourseVo();
            BeanUtils.copyProperties(model,vo);
            Integer count = baseMapper.selectCount(new QueryWrapper<CourseStudent>().eq("course_id",courseId).eq("ruby_class_id",model.getId()));
            if(count>0){
                vo.setChoose(true);
            }else {
                vo.setChoose(false);
            }
            voList.add(vo);
        }

        return voList;
    }

    @Transactional
    @Override
    public boolean removeByCourseId(Long courseId, Long rubyClassId) {
        Course course = courseMapper.selectById(courseId); //获取课程实体
        course.setCount(course.getCount()-1); //选课班级-1
        courseMapper.updateById(course);
        QueryWrapper<CourseStudent> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId).eq("ruby_class_id",rubyClassId);
        if(baseMapper.delete(wrapper)>0) return true;
        return false;
    }

    @Override
    public Page<Student> listPageStudent(Page<Student> studentPage, Long rubyClassId) {
        Page<Student> list = studentMapper.selectPage(studentPage, new QueryWrapper<Student>().eq("ruby_class_id", rubyClassId));
        return list;
    }

    @Override
    public  ArrayList<Student>  randomRoll(Long rubyClassId) {
        ArrayList<Student> list = new ArrayList<>();
        List<Student> studentList = studentMapper.selectList(new QueryWrapper<Student>().eq("ruby_class_id", rubyClassId));
        if(studentList.size()<=0) return null;
        if(studentList.size()-1==0) {
            Student student = studentList.get(0);
            list.add(student);
            return list;
        }
        Random random = new Random();
        int number = random.nextInt(studentList.size() - 1); //生成随机下标
        Student student = studentList.get(number);
        list.add(student);
        return list;
    }

}
