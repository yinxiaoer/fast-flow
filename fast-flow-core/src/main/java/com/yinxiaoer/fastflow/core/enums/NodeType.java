package com.yinxiaoer.fastflow.core.enums;

/**
 * 节点类型
 *
 * @author yinxiuhe
 * @date 2021/11/19 16:29
 */
public enum NodeType {

    /**
     * 普通节点
     */
    NORMAL(0),
    /**
     * 并行节点
     */
    PARALLEL(1),
    /**
     * 事件节点
     */
    EVENT(2);

    private final int code;

    NodeType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
