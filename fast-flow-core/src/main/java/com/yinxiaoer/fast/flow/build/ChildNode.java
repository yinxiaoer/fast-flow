package com.yinxiaoer.fast.flow.build;

import com.yinxiaoer.fast.flow.context.FlowNode;
import com.yinxiaoer.fast.flow.function.FlowCondition;
import lombok.Data;

import java.io.Serializable;

/**
 * 子节点
 *
 * @author yinxiuhe
 * @date 2021/3/25 14:36
 */
@Data
public class ChildNode implements Serializable {

    /**
     * 条件
     */
    private FlowCondition condition;
    /**
     * 节点坐标(执行顺序，从小到大执行)
     */
    private int x;
    /**
     * 节点
     */
    private FlowNode node;

    public ChildNode() {
    }

    public ChildNode(FlowCondition condition, FlowNode node) {
        this.condition = condition;
        this.x = node.getX();
        this.node = node;
    }

    public String getNodeKey() {
        return this.node.getKey();
    }
}
