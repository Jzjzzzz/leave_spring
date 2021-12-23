package com.gxuwz.leave.entity.vo;

import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/12/21 1:06
 * @Version 1.0
 * 班级显示
 */
@Data
public class RubyClassCourseVo {
    private Long id;
    private String name;
    private String specialized;
    private Integer grade;
    private Integer number;

    /**
     * 该班级是否已选课
     */
    private boolean isChoose;
}
