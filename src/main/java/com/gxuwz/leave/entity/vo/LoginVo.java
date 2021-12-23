package com.gxuwz.leave.entity.vo;

import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/12/22 0:02
 * @Version 1.0
 * 登录实体
 */
@Data
public class LoginVo {
    private String username;
    private String password;
    private int role;

}
