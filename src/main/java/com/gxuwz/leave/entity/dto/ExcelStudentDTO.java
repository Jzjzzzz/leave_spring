package com.gxuwz.leave.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/12/22 22:44
 * @Version 1.0
 */
@Data
public class ExcelStudentDTO {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private String sex;
    @ExcelProperty("邮箱")
    private String email;
    @ExcelProperty("班级id")
    private Long rubyClassId;
}
