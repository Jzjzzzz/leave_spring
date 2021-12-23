package com.gxuwz.leave.mapper;

import com.gxuwz.leave.entity.RubyClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxuwz.leave.entity.vo.RubyClassVo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 班级 Mapper 接口
 * </p>
 *
 * @author Jzj
 * @since 2021-12-17
 */
public interface RubyClassMapper extends BaseMapper<RubyClass> {

    RubyClassVo getBaseRubyClassList(Long id);
}
