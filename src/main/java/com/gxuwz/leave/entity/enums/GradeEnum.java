package com.gxuwz.leave.entity.enums;

/**
 * @Author Jzj
 * @Date 2021/12/17 17:40
 * @Version 1.0
 * 年级枚举类
 */
public enum GradeEnum {
    FIRST_GRADE("大一",1),SECOND_GRADE("大二",2),GRADE_THREE("大三",3),SENIOR_CLASS("大四",4);

    private String name;
    private Integer number;

    GradeEnum(String name,Integer number) {
        this.number = number;
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public Integer getNumber(){
        return number;
    }


}
