package com.yinxiaoer.fastflow.core.api;


import com.yinxiaoer.fastflow.core.engine.container.FlowContext;

/**
 * 流程节点around 接口
 * 自定义around需实现该接口
 *
 * @author yinxiuhe
 * @date 2021/11/19 14:49
 */
public interface NodeAround {

    /**
     * 节点前置处理
     *
     * @param flowContext 流程上下文
     * @return 返回对象在上下文中key=节点名+Before
     */
    default void before(FlowContext flowContext) {
    }

    /**
     * 节点后置处理
     *
     * @param flowContext 流程上下文
     * @param nodeResult  节点结果
     * @return 返回对象在上下文中会替换节点对象
     */
    default Object after(FlowContext flowContext, Object nodeResult) {
        return null;
    }
}
