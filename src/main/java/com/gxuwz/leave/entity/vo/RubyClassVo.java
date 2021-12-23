package com.gxuwz.leave.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author Jzj
 * @Date 2021/12/20 18:34
 * @Version 1.0
 */
@Data
public class RubyClassVo {
    private Long id;
    private String name;
    private String specialized;
    private Integer grade;
    private Integer number;
    private String collegeName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

}
