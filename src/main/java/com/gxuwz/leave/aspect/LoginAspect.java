package com.gxuwz.leave.aspect;

import com.gxuwz.leave.entity.vo.UserInfoVO;
import com.gxuwz.leave.exception.BusinessException;
import com.gxuwz.leave.util.JwtUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Jzj
 * @Date 2021/12/22 20:22
 * @Version 1.0
 * 统一权限控制切面处理
 */

public class LoginAspect {





    public void doBefore() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");
        if(token.equals("") && token==null) throw new BusinessException("该用户暂未登录无法访问");
        UserInfoVO userInfoVO = JwtUtils.getUserJwtToken(request);

        int role = userInfoVO.getType();
        if(role==2) throw new BusinessException("权限不足，无法访问");
        System.out.println("走拦截器");

    }




}
