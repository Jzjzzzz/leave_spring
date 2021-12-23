package com.gxuwz.leave.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Student;
import com.gxuwz.leave.entity.query.StudentQuery;
import com.gxuwz.leave.exception.BusinessException;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;

/**
 * <p>
 * 学生 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-12-19
 */
@RestController
@RequestMapping("/teacher/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/listPage/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) StudentQuery studentQuery){
        HashMap<String,Object> map = studentService.listPage(new Page<>(page,limit), studentQuery);
        return R.ok().data("map",map);
    }

    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = studentService.removeRubyClassById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Student model = studentService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @PostMapping("/save")
    public R saveCollege(@RequestBody Student student){
        boolean result = studentService.saveStudent(student);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @PutMapping("/update")
    public R update(@RequestBody Student student){
        boolean result = studentService.updateStudentById(student);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }

    @PostMapping("/import")
    public R batchImport(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            studentService.importData(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            throw new BusinessException("文件上传错误");
        }
    }
}

