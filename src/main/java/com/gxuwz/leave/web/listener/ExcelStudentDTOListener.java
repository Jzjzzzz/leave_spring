package com.gxuwz.leave.web.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.leave.entity.RubyClass;
import com.gxuwz.leave.entity.Student;
import com.gxuwz.leave.entity.dto.ExcelStudentDTO;
import com.gxuwz.leave.mapper.RubyClassMapper;
import com.gxuwz.leave.mapper.StudentMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;


import java.util.Date;


/**
 * @Author Jzj
 * @Date 2021/12/22 22:45
 * @Version 1.0
 */
@Slf4j
@NoArgsConstructor //无参
public class ExcelStudentDTOListener extends AnalysisEventListener<ExcelStudentDTO> {

    private StudentMapper studentMapper;
    private RubyClassMapper rubyClassMapper;
    //传入mapper对象
    public ExcelStudentDTOListener(StudentMapper studentMapper,RubyClassMapper rubyClassMapper) {
        this.studentMapper = studentMapper;
        this.rubyClassMapper = rubyClassMapper;
    }

    @Override
    public void invoke(ExcelStudentDTO data, AnalysisContext analysisContext) {
        log.info("解析到一条记录: {}", data);

        //封装学生类
        Student student = new Student();
        BeanUtils.copyProperties(data,student);
        student.setGmtCreate(new Date());
        student.setGmtModified(new Date());
        student.setStatus(1);
        studentMapper.insert(student);

        //修改班级信息
        RubyClass rubyClass = rubyClassMapper.selectOne(new QueryWrapper<RubyClass>().eq("id", student.getRubyClassId()));
        rubyClass.setNumber(rubyClass.getNumber()+1);
        rubyClassMapper.updateById(rubyClass);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


}
