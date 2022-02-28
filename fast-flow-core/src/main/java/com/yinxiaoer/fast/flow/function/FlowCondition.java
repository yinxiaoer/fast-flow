package com.yinxiaoer.fast.flow.function;

import com.yinxiaoer.fast.flow.context.FlowContext;

/**
 * 流程条件方法
 *
 * @author yinxiuhe
 * @date 2022/2/28 11:17
 */
@FunctionalInterface
public interface FlowCondition {

    /**
     * 流程条件方法
     *
     * @param context 流程上下文
     * @return 条件是否成立
     */
    boolean test(FlowContext context);

}
