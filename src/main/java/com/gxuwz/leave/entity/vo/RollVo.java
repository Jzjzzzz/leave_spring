package com.gxuwz.leave.entity.vo;

import com.gxuwz.leave.entity.Student;
import lombok.Data;

import java.util.List;

/**
 * @Author Jzj
 * @Date 2021/12/21 17:30
 * @Version 1.0
 * 点名vo
 *
 */
@Data
public class RollVo {

    private List<Student> students; //学生列表
}
