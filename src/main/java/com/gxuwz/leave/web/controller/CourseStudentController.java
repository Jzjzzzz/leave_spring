package com.gxuwz.leave.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Course;
import com.gxuwz.leave.entity.Student;
import com.gxuwz.leave.entity.query.CourseQuery;
import com.gxuwz.leave.entity.vo.RubyClassCourseVo;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.CourseStudentService;
import com.gxuwz.leave.service.RollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 选课 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
@RestController
@RequestMapping("/teacher/course-student")
@CrossOrigin
public class CourseStudentController {

    @Autowired
    private CourseStudentService courseStudentService;

    @Autowired
    private RollService rollService;

    @PostMapping("/listPage/{page}/{limit}/{teacherId}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit,@PathVariable Long teacherId, @RequestBody(required = false) CourseQuery courseQuery){
        HashMap<String,Object> map = courseStudentService.listPage(new Page<Course>(page,limit), courseQuery,teacherId);
        return R.ok().data("map",map);
    }

    @PostMapping("/curricula/{courseId}/{rubyClassId}")
    public R curricula(@PathVariable Long courseId,@PathVariable Long rubyClassId){
        boolean result = courseStudentService.curricula(courseId,rubyClassId);
        if(result) return R.ok().message("选课成功");
        return R.error().message("选课失败");
    }

    @GetMapping("/getRubyClassList/{courseId}")
    public R getRubyClassList(@PathVariable Long courseId){
        List<RubyClassCourseVo> rubyClassList = courseStudentService.getRubyClassList(courseId);
        return R.ok().data("rubyClassList",rubyClassList);
    }

    @DeleteMapping("/removeByCourseId/{courseId}/{rubyClassId}")
    public R removeByCourseId(@PathVariable Long courseId,@PathVariable Long rubyClassId){
        boolean result = courseStudentService.removeByCourseId(courseId,rubyClassId);
        if(result) return R.ok().message("退选成功");
        return R.error().message("退选失败");
    }

    /**
     * 全班一起点名
     * @param page
     * @param limit
     * @param rubyClassId
     * @return
     */
    @PostMapping("/listPageStudent/{page}/{limit}/{rubyClassId}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit,@PathVariable Long rubyClassId){
        Page<Student> list = courseStudentService.listPageStudent(new Page<Student>(page,limit),rubyClassId);
        return R.ok().data("list",list);
    }

    /**
     * 随机抽取一名同学
     * @param rubyClassId
     * @return
     */
    @PostMapping("/randomRoll/{rubyClassId}")
    public R randomRoll(@PathVariable Long rubyClassId){
        ArrayList<Student> list = courseStudentService.randomRoll(rubyClassId);
        if(list==null) return R.error().message("该班级没有学生");
        return R.ok().data("list",list);

    }

    @PostMapping("/rollBook")
    public R rollBook(@RequestBody(required = false) List<Student> studentList){
        boolean result = rollService.saveRoll(studentList);
        if(result) return R.ok().message("点名成功");
        return R.error().message("点名失败");
    }






}

