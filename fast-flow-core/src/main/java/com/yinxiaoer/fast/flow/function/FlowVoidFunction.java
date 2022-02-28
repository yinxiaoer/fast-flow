package com.yinxiaoer.fast.flow.function;

import com.yinxiaoer.fast.flow.context.FlowContext;
import com.yinxiaoer.fast.flow.context.FlowNode;

/**
 * 无返回值流程方法
 *
 * @author yinxiuhe
 * @date 2022/2/28 11:25
 */
@FunctionalInterface
public interface FlowVoidFunction extends FlowFunction {

    /**
     * 流程方法
     *
     * @param context 流程上下文
     * @param node 流程节点
     * @return 节点返回值
     */
    @Override
    default Object apply(FlowContext context, FlowNode node){
        accept(context, node);
        return null;
    }

    /**
     * 无返回值流程方法
     *
     * @param context 流程上下文
     * @param node 流程节点
     */
    void accept(FlowContext context, FlowNode node);

}
