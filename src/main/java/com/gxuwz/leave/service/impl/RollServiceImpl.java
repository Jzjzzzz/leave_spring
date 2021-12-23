package com.gxuwz.leave.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Course;
import com.gxuwz.leave.entity.Roll;
import com.gxuwz.leave.entity.Student;
import com.gxuwz.leave.mapper.RollMapper;
import com.gxuwz.leave.service.RollService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  点名记录服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-21
 */
@Service
public class RollServiceImpl extends ServiceImpl<RollMapper, Roll> implements RollService {



    @Transactional
    @Override
    public boolean saveRoll(List<Student> studentList) {

        //封装未签到人员
        for (Student student : studentList) {
            Roll roll = new Roll();
            roll.setGmtCreate(new Date());
            roll.setGmtModified(new Date());
            roll.setStatus(1);
            roll.setStudentId(student.getId()); //学生id
            roll.setStudentName(student.getName()); //学生姓名
            baseMapper.insert(roll);
        }
        return true;
    }

    @Override
    public Page<Roll> listPage(Page<Roll> rollPage) {
        Page<Roll> list = baseMapper.selectPage(rollPage,null);
        return list;
    }


}
