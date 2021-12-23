package com.gxuwz.leave.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Course;
import com.gxuwz.leave.entity.Roll;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxuwz.leave.entity.Student;

import java.util.List;

/**
 * <p>
 *  点名记录服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-21
 */
public interface RollService extends IService<Roll> {

    boolean saveRoll(List<Student> studentList);

    Page<Roll> listPage(Page<Roll> rollPage);
}
