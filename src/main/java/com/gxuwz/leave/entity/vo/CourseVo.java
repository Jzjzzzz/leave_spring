package com.gxuwz.leave.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author Jzj
 * @Date 2021/12/20 0:47
 * @Version 1.0
 */
@Data
public class CourseVo {
    private Long id;
    private String name;
    private Integer semester;
    private Integer hours;
    private Integer number;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 教师名称
     */
    private String teacherName;
}
