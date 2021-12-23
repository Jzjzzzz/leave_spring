package com.gxuwz.leave.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Roll;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.RollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  点名记录前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-12-21
 */
@RestController
@RequestMapping("/teacher/roll")
@CrossOrigin
public class RollController {

    @Autowired
    private RollService rollService;

    @PostMapping("/listPage/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit){
        Page<Roll> listPage = rollService.listPage(new Page<Roll>(page,limit));
        return R.ok().data("listPage",listPage);
    }

    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = rollService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }
}

