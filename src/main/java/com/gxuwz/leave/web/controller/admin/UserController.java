package com.gxuwz.leave.web.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.User;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  管理员用户前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-12-21
 */
@RestController
@RequestMapping("/admin/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/listPage/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit){
        IPage<User> listPage = userService.listPage(new Page<User>(page,limit));
        return R.ok().data("listPage",listPage);
    }

    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = userService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        User model = userService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @PostMapping("/save")
    public R saveCollege(@RequestBody User user){
        boolean result = userService.saveUser(user);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @PutMapping("/update")
    public R update(@RequestBody User user){
        boolean result = userService.updateUserById(user);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }



}

