package com.gxuwz.leave.web.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.College;
import com.gxuwz.leave.entity.query.CollegeQuery;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 二级学院 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-12-14
 */
@RestController
@RequestMapping("/admin/college")
@CrossOrigin
public class CollegeController {
    @Autowired
    private CollegeService collegeService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/listPage/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) CollegeQuery collegeQuery){
        IPage<College> listPage = collegeService.listPage(new Page<>(page,limit), collegeQuery);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        return R.ok().data("listPage",listPage);
    }

    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = collegeService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        College model = collegeService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @PostMapping("/save")
    public R saveCollege(@RequestBody College college){
        boolean result = collegeService.saveCollege(college);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @PutMapping("/update")
    public R update(@RequestBody College college){
        boolean result = collegeService.updateCollegeById(college);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }
}

