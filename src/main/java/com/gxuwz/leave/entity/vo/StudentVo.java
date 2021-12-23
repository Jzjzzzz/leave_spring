package com.gxuwz.leave.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author Jzj
 * @Date 2021/12/19 5:33
 * @Version 1.0
 * 学生列表
 */
@Data
public class StudentVo {


    private Long id;
    private String name;
    private String sex;
    private String email;
    private Long rubyClassId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    private String rubyClassName;
}
