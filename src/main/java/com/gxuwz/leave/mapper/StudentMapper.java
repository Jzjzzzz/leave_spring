package com.gxuwz.leave.mapper;

import com.gxuwz.leave.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxuwz.leave.entity.dto.ExcelStudentDTO;
import com.gxuwz.leave.entity.vo.StudentVo;

import java.util.List;

/**
 * <p>
 * 学生 Mapper 接口
 * </p>
 *
 * @author Jzj
 * @since 2021-12-19
 */
public interface StudentMapper extends BaseMapper<Student> {
    StudentVo getBaseStudentList(Long id);

    void insertBatch(List<ExcelStudentDTO> list);
}
