package com.gxuwz.leave.web.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.RubyClass;
import com.gxuwz.leave.entity.Teacher;
import com.gxuwz.leave.entity.query.RubyClassQuery;
import com.gxuwz.leave.entity.query.TeacherQuery;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 教师 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
@RestController
@RequestMapping("/admin/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/listPage/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) TeacherQuery teacherQuery){
        IPage<Teacher> listPage = teacherService.listPage(new Page<Teacher>(page,limit), teacherQuery);
        return R.ok().data("listPage",listPage);
    }

    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = teacherService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Teacher model = teacherService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @PostMapping("/save")
    public R saveCollege(@RequestBody Teacher teacher){
        boolean result = teacherService.saveTeacher(teacher);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @PutMapping("/update")
    public R update(@RequestBody Teacher teacher){
        boolean result = teacherService.updateTeacherById(teacher);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }

}

