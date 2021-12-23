package com.gxuwz.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxuwz.leave.entity.vo.LoginVo;
import com.gxuwz.leave.entity.vo.UserInfoVO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-21
 */
public interface UserService extends IService<User> {
    

    boolean saveUser(User user);

    boolean updateUserById(User user);

    IPage<User> listPage(Page<User> userPage);

    String loginByType(LoginVo loginVo);

    UserInfoVO getUserInfo(HttpServletRequest request);
}
