package com.gxuwz.leave.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.College;
import com.gxuwz.leave.entity.query.CollegeQuery;
import com.gxuwz.leave.mapper.CollegeMapper;
import com.gxuwz.leave.service.CollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 二级学院 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-14
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {


    @Override
    public IPage<College> listPage(Page<College> pageParam, CollegeQuery collegeQuery) {
        if (collegeQuery == null) return baseMapper.selectPage(pageParam, null);
        String name = collegeQuery.getName();
        return baseMapper.selectPage(pageParam, new QueryWrapper<College>().like(StringUtils.isNotBlank(name), "name", name));
    }

    @Override
    public boolean saveCollege(College college) {
        college.setGmtModified(new Date());
        college.setGmtCreate(new Date());
        college.setStatus(1);
        if(baseMapper.insert(college)>0) return true;
        return false;
    }

    @Override
    public boolean updateCollegeById(College college) {
        college.setGmtModified(new Date());
        if(baseMapper.updateById(college)>0) return true;
        return false;
    }
}
