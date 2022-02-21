package com.yinxiaoer.fastflow.core.enums;

/**
 * 默认节点
 *
 * @author yinxiuhe
 * @date 2021/11/19 16:28
 */
public enum DefaultNode {

    /**
     * 开始节点
     */
    START("start", "开始"),
    /**
     * 结束节点
     */
    END("end", "结束");

    /**
     * 节点key
     */
    private final String key;
    /**
     * 节点名称
     */
    private final String name;

    DefaultNode(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
