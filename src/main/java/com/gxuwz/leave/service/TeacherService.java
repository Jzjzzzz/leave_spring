package com.gxuwz.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxuwz.leave.entity.query.TeacherQuery;
import com.gxuwz.leave.entity.vo.LoginVo;

/**
 * <p>
 * 教师 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
public interface TeacherService extends IService<Teacher> {

    IPage<Teacher> listPage(Page<Teacher> teacherPage, TeacherQuery teacherQuery);

    boolean saveTeacher(Teacher teacher);

    boolean updateTeacherById(Teacher teacher);

    Teacher loginByType(LoginVo loginVo);
}
