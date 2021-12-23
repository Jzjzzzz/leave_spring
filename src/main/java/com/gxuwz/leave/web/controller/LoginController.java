package com.gxuwz.leave.web.controller;

import com.gxuwz.leave.entity.Teacher;
import com.gxuwz.leave.entity.User;
import com.gxuwz.leave.entity.vo.LoginVo;
import com.gxuwz.leave.entity.vo.UserInfoVO;
import com.gxuwz.leave.result.R;
import com.gxuwz.leave.service.TeacherService;
import com.gxuwz.leave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Jzj
 * @Date 2021/12/21 23:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/teacher/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private UserService userService;


    @PostMapping("/loginByType")
    public R loginByType(@RequestBody LoginVo loginVo){
        String token = userService.loginByType(loginVo);
        return R.ok().data("token",token);
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/getInfo")
    public R getInfo(HttpServletRequest request){
        UserInfoVO userInfo = userService.getUserInfo(request);
        return R.ok().data("userInfo",userInfo);
    }
}
