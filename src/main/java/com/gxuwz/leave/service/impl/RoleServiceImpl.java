package com.gxuwz.leave.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.College;
import com.gxuwz.leave.entity.Role;
import com.gxuwz.leave.mapper.RoleMapper;
import com.gxuwz.leave.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {



    @Override
    public boolean saveRole(Role role) {
        role.setGmtModified(new Date());
        role.setGmtCreate(new Date());
        role.setStatus(1);
        if(baseMapper.insert(role)>0) return true;
        return false;
    }

    @Override
    public boolean updateRoleById(Role role) {
        role.setGmtModified(new Date());
        if(baseMapper.updateById(role)>0) return true;
        return false;
    }

    @Override
    public IPage<Role> listPage(Page<Role> rolePage) {
        Page<Role> list = baseMapper.selectPage(rolePage, null);
        return list;
    }
}
