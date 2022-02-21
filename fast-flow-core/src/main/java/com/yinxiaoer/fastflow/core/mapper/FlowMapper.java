package com.yinxiaoer.fastflow.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yinxiaoer.fastflow.core.entity.Flow;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程mapper
 * @author yinxiuhe
 * @date 2021/11/19 14:48
 */
@Mapper
public interface FlowMapper extends BaseMapper<Flow> {
}
