package com.gxuwz.leave.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.RubyClass;
import com.gxuwz.leave.entity.enums.GradeEnum;
import com.gxuwz.leave.entity.query.RubyClassQuery;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.RubyClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * <p>
 * 班级 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-12-17
 */
@RestController
@RequestMapping("/teacher/rubyClass")
@CrossOrigin
public class RubyClassController {


    @Autowired
    private RubyClassService rubyClassService;

    @PostMapping("/listPage/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) RubyClassQuery query){
        HashMap<String,Object> map = rubyClassService.listPage(new Page<RubyClass>(page,limit), query);
        return R.ok().data("map",map);
    }

    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = rubyClassService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        RubyClass model = rubyClassService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @PostMapping("/save")
    public R saveCollege(@RequestBody RubyClass rubyClass){
        boolean result = rubyClassService.saveClass(rubyClass);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @PutMapping("/update")
    public R update(@RequestBody RubyClass rubyClass){
        boolean result = rubyClassService.updateClassById(rubyClass);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }



}

