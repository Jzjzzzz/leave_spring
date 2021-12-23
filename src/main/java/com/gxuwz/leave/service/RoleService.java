package com.gxuwz.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-21
 */
public interface RoleService extends IService<Role> {


    boolean saveRole(Role role);

    boolean updateRoleById(Role role);

    IPage<Role> listPage(Page<Role> rolePage);
}
