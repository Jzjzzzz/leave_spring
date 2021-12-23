package com.gxuwz.leave.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 选课
 * </p>
 *
 * @author Jzj
 * @since 2021-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 选课表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 班级id
     */
    private Long rubyClassId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 状态
     */
    private Integer status;


}
