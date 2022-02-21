package com.yinxiaoer.fastflow.core.engine;

import com.yinxiaoer.fastflow.core.entity.FlowNode;
import lombok.Data;

import java.io.Serializable;

/**
 * 子节点
 *
 * @author yinxiuhe
 * @date 2021/11/19 16:28
 */
@Data
public class ChildNode implements Serializable {

    /**
     * 条件
     */
    private String condition;
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

    public ChildNode(String condition, FlowNode node) {
        this.condition = condition;
        this.x = node.getX();
        this.node = node;
    }

    public String getNodeKey() {
        return this.node.getKey();
    }
}
