package com.gxuwz.leave.web.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.College;
import com.gxuwz.leave.entity.Role;
import com.gxuwz.leave.entity.query.CollegeQuery;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  角色前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-12-21
 */
@RestController
@RequestMapping("/admin/role")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/listPage/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit){
        IPage<Role> listPage = roleService.listPage(new Page<Role>(page,limit));
        return R.ok().data("listPage",listPage);
    }

    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = roleService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Role model = roleService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @PostMapping("/save")
    public R saveRole(@RequestBody Role role){
        boolean result = roleService.saveRole(role);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @PutMapping("/update")
    public R update(@RequestBody Role role){
        boolean result = roleService.updateRoleById(role);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }
}

