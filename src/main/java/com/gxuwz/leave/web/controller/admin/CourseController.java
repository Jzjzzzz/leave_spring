package com.gxuwz.leave.web.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Course;
import com.gxuwz.leave.entity.query.CourseQuery;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
@RestController
@RequestMapping("/admin/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/listPage/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) CourseQuery courseQuery){
        HashMap<String,Object> map = courseService.listPage(new Page<Course>(page,limit), courseQuery);
        return R.ok().data("map",map);
    }

    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = courseService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Course model = courseService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @PostMapping("/save")
    public R saveCollege(@RequestBody Course course){
        boolean result = courseService.saveCourse(course);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @PutMapping("/update")
    public R update(@RequestBody Course course){
        boolean result = courseService.updateCourseById(course);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }


}

