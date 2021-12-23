package com.gxuwz.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.College;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxuwz.leave.entity.query.CollegeQuery;

/**
 * <p>
 * 二级学院 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-14
 */
public interface CollegeService extends IService<College> {


    IPage<College> listPage(Page<College> collegePage, CollegeQuery collegeQuery);

    boolean saveCollege(College college);

    boolean updateCollegeById(College college);
}
