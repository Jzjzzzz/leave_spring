package com.gxuwz.leave.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.RubyClass;
import com.gxuwz.leave.entity.Teacher;
import com.gxuwz.leave.entity.User;
import com.gxuwz.leave.entity.query.TeacherQuery;
import com.gxuwz.leave.entity.vo.LoginVo;
import com.gxuwz.leave.exception.BusinessException;
import com.gxuwz.leave.mapper.TeacherMapper;
import com.gxuwz.leave.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 教师 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public IPage<Teacher> listPage(Page<Teacher> teacherPage, TeacherQuery teacherQuery) {
        if (teacherQuery == null) return baseMapper.selectPage(teacherPage, null);
        String name = teacherQuery.getName();
        return baseMapper.selectPage(teacherPage, new QueryWrapper<Teacher>().like(StringUtils.isNotBlank(name), "name", name));
    }

    @Override
    public boolean saveTeacher(Teacher teacher) {
        teacher.setGmtModified(new Date());
        teacher.setGmtCreate(new Date());
        teacher.setStatus(1);
        if(baseMapper.insert(teacher)>0) return true;
        return false;
    }

    @Override
    public boolean updateTeacherById(Teacher teacher) {
        teacher.setGmtModified(new Date());
        if(baseMapper.updateById(teacher)>0) return true;
        return false;
    }

    @Override
    public Teacher loginByType(LoginVo loginVo) {
        String username = loginVo.getUsername(); //用户名
        String password = loginVo.getPassword(); //密码
        Teacher teacher = baseMapper.selectOne(new QueryWrapper<Teacher>().eq("user_account", username).eq("password", password));
        if(teacher==null) throw new BusinessException("账号密码不存在，请重新输入");
        return teacher;
    }
}
