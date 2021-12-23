package com.gxuwz.leave.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Role;
import com.gxuwz.leave.entity.Teacher;
import com.gxuwz.leave.entity.User;
import com.gxuwz.leave.entity.vo.LoginVo;
import com.gxuwz.leave.entity.vo.UserInfoVO;
import com.gxuwz.leave.exception.BusinessException;
import com.gxuwz.leave.mapper.RoleMapper;
import com.gxuwz.leave.mapper.TeacherMapper;
import com.gxuwz.leave.mapper.UserMapper;
import com.gxuwz.leave.service.TeacherService;
import com.gxuwz.leave.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxuwz.leave.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public boolean saveUser(User user) {
        user.setGmtModified(new Date());
        user.setGmtCreate(new Date());
        user.setStatus(1);
        if(baseMapper.insert(user)>0) return true;
        return false;
    }

    @Override
    public boolean updateUserById(User user) {
        user.setGmtModified(new Date());
        if(baseMapper.updateById(user)>0) return true;
        return false;
    }

    @Override
    public IPage<User> listPage(Page<User> userPage) {
        Page<User> list = baseMapper.selectPage(userPage, null);
        return list;
    }

    @Override
    public String  loginByType(LoginVo loginVo) {
        int type = loginVo.getRole(); //角色(1为管理员，2为用户)
        String username = loginVo.getUsername(); //用户名
        String password = loginVo.getPassword(); //密码
        String token;
        if(type==1){
            User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username).eq("password", password));
            if(user==null) throw new BusinessException("账号密码不存在，请重新输入");
            token = JwtUtils.getJwtToken(user.getId(), user.getUsername(),type);
        }else {
            Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("user_account", username).eq("password", password));
            if(teacher==null) throw new BusinessException("账号密码不存在，请重新输入");
            token = JwtUtils.getJwtToken(teacher.getId(), teacher.getName(),type);
        }
        return token;
    }

    @Override
    public UserInfoVO getUserInfo(HttpServletRequest request) {
        UserInfoVO userInfoVO = JwtUtils.getUserJwtToken(request);
        return userInfoVO;
    }
}
