package com.gxuwz.leave.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.leave.entity.College;
import com.gxuwz.leave.entity.RubyClass;
import com.gxuwz.leave.entity.query.RubyClassQuery;
import com.gxuwz.leave.entity.vo.RubyClassVo;
import com.gxuwz.leave.mapper.CollegeMapper;
import com.gxuwz.leave.mapper.RubyClassMapper;
import com.gxuwz.leave.service.RubyClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 班级 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-12-17
 */
@Service
public class RubyClassServiceImpl extends ServiceImpl<RubyClassMapper, RubyClass> implements RubyClassService {

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public HashMap<String, Object> listPage(Page<RubyClass> rubyClassPage, RubyClassQuery query) {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<RubyClassVo> list = new ArrayList<>();
        QueryWrapper<RubyClass> wrapper = new QueryWrapper<>();
        //封装二级学院列表
        List<College> collegeList = collegeMapper.selectList(null);
        map.put("collegeList",collegeList);

        if(!org.springframework.util.StringUtils.isEmpty(query)){
            if(!org.springframework.util.StringUtils.isEmpty(query.getName())){
                wrapper.like("name",query.getName());
            }
        }
        baseMapper.selectPage(rubyClassPage,wrapper);
        List<RubyClass> records = rubyClassPage.getRecords();
        RubyClassVo rubyClassVo;
        for (RubyClass item : records) {
            //封装数据
            rubyClassVo = baseMapper.getBaseRubyClassList(item.getId());
            list.add(rubyClassVo);
        }
        map.put("collegeList",collegeList);
        map.put("list",list);
        map.put("total",rubyClassPage.getTotal());
        return map;
    }

    @Override
    public boolean saveClass(RubyClass rubyClass) {
        rubyClass.setGmtModified(new Date());
        rubyClass.setGmtCreate(new Date());
        rubyClass.setStatus(1);
        rubyClass.setNumber(0);
        if(baseMapper.insert(rubyClass)>0) return true;
        return false;
    }

    @Override
    public boolean updateClassById(RubyClass rubyClass) {
        rubyClass.setGmtModified(new Date());
        if(baseMapper.updateById(rubyClass)>0) return true;
        return false;
    }
}
