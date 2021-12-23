package com.gxuwz.leave.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.RubyClass;
import com.gxuwz.leave.entity.Student;
import com.gxuwz.leave.entity.dto.ExcelStudentDTO;
import com.gxuwz.leave.entity.query.StudentQuery;
import com.gxuwz.leave.entity.vo.StudentVo;
import com.gxuwz.leave.mapper.RubyClassMapper;
import com.gxuwz.leave.mapper.StudentMapper;
import com.gxuwz.leave.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxuwz.leave.web.listener.ExcelStudentDTOListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 学生 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-19
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private RubyClassMapper rubyClassMapper;



    @Override
    public HashMap<String,Object> listPage(Page<Student> studentPage, StudentQuery studentQuery) {
        ArrayList<StudentVo> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(studentQuery)){
            if(!StringUtils.isEmpty(studentQuery.getName())){
                wrapper.like("name",studentQuery.getName());
            }
        }
        baseMapper.selectPage(studentPage,wrapper);
        List<Student> records = studentPage.getRecords();
        StudentVo studentVo;
        for (Student item : records) {
            //封装数据
            studentVo = baseMapper.getBaseStudentList(item.getId());
            list.add(studentVo);
        }
        //查询班级列表
        List<RubyClass> RubyClassList = rubyClassMapper.selectList(null);
        map.put("RubyClassList",RubyClassList);
        map.put("list",list);
        map.put("total",studentPage.getTotal());
        return map;
    }

    @Transactional
    @Override
    public boolean saveStudent(Student student) {
        student.setGmtModified(new Date());
        student.setGmtCreate(new Date());
        student.setStatus(1);
        RubyClass rubyClass = rubyClassMapper.selectOne(new QueryWrapper<RubyClass>().eq("id", student.getRubyClassId()));
        rubyClass.setNumber(rubyClass.getNumber()+1);
        rubyClassMapper.updateById(rubyClass);
        if(baseMapper.insert(student)>0) return true;
        return false;
    }

    @Transactional
    @Override
    public boolean updateStudentById(Student student) {
        student.setGmtModified(new Date());
        if(baseMapper.updateById(student)>0) return true;
        return false;
    }

    @Transactional
    @Override
    public boolean removeRubyClassById(Long id) {
        Student student = baseMapper.selectOne(new QueryWrapper<Student>().eq("id", id));
        RubyClass rubyClass = rubyClassMapper.selectOne(new QueryWrapper<RubyClass>().eq("id", student.getRubyClassId()));
        rubyClass.setNumber(rubyClass.getNumber()-1);
        rubyClassMapper.updateById(rubyClass);
        if(baseMapper.deleteById(student)>0) return true;
        return false;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, ExcelStudentDTO.class, new ExcelStudentDTOListener(baseMapper,rubyClassMapper)).sheet().doRead();
    }
}
