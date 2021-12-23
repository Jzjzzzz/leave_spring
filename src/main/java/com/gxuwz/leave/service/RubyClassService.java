package com.gxuwz.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.College;
import com.gxuwz.leave.entity.RubyClass;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxuwz.leave.entity.query.RubyClassQuery;

import java.util.HashMap;

/**
 * <p>
 * 班级 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-17
 */
public interface RubyClassService extends IService<RubyClass> {



    boolean saveClass(RubyClass rubyClass);

    boolean updateClassById(RubyClass rubyClass);

    HashMap<String, Object> listPage(Page<RubyClass> rubyClassPage, RubyClassQuery query);
}
