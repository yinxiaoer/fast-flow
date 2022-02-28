package com.yinxiaoer.fast.flow.context;

import com.yinxiaoer.fast.flow.enums.NodeType;
import com.yinxiaoer.fast.flow.function.FlowFunction;
import lombok.Data;

import java.io.Serializable;

/**
 * 流程节点
 *
 * @author yinxiuhe
 * @date 2022/2/28 11:05
 */
@Data
public class FlowNode implements Serializable {

    /**
     * 节点key
     */
    private String key;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private int type = NodeType.NORMAL.getCode();
    /**
     * 执行方法
     */
    private FlowFunction function;
    /**
     * 重试次数
     */
    private int retry = 3;
    /**
     * 节点坐标
     */
    private int x;

    public FlowNode(String key) {
        this.key = key;
        this.name = key;
    }

    public FlowNode(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public FlowNode(String key, FlowFunction function) {
        this.key = key;
        this.name = key;
        this.function = function;
    }

    public FlowNode(String key, String name, FlowFunction function) {
        this.key = key;
        this.name = name;
        this.function = function;
    }

    public FlowNode(String key, NodeType nodeType, FlowFunction function) {
        this.key = key;
        this.name = key;
        this.type = nodeType.getCode();
        this.function = function;
    }

    public FlowNode(String key, String name, NodeType nodeType, FlowFunction function) {
        this.key = key;
        this.name = name;
        this.type = nodeType.getCode();
        this.function = function;
    }

}
