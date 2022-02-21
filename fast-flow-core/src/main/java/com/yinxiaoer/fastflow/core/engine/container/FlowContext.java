package com.yinxiaoer.fastflow.core.engine.container;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 流程上下文
 *
 * @author yinxiuhe
 * @date 2021/11/19 14:49
 */
@ToString
@NoArgsConstructor
public class FlowContext implements Serializable {

    /**
     * 流程变量
     */
    private final Map<String, Object> variables = new ConcurrentHashMap<>();

    public FlowContext(Map<String, Object> vars) {
        variables.putAll(vars);
    }

    /**
     * 添加变量
     *
     * @param key   变量key
     * @param value 变量值
     */
    public void put(String key, Object value) {
        //为空则不放入流程变量中
        if (value == null) {
            return;
        }
        variables.put(key, value);
    }

    /**
     * 批量添加流程变量
     */
    public void putAll(Map<String, Object> vars) {
        variables.putAll(vars);
    }


    /**
     * 获取节点
     *
     * @param nodeKey 节点key
     */
    public Object get(String nodeKey) {
        return variables.get(nodeKey);
    }

    /**
     * @return 所有流程变量
     */
    public Map<String, Object> getVariables() {
        return variables;
    }
}
