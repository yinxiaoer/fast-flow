package com.yinxiaoer.fast.flow.context;

import cn.hutool.core.map.MapUtil;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 流程上下文
 *
 * @author yinxiuhe
 * @date 2021/3/25 17:40
 */
@ToString
@NoArgsConstructor
public class FlowContext implements Serializable {

    /**
     * 节点前缀
     */
    private final static String NODE_PREFIX = "NODE_RESULT_";
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
     * 设置节点结果
     *
     * @param nodeKey    节点key
     * @param nodeResult 节点结果
     */
    public void putNodeResult(String nodeKey, Object nodeResult) {
        String key = NODE_PREFIX + nodeKey;
        put(key, nodeResult);
    }

    /**
     * 批量添加流程变量
     */
    public void putAll(Map<String, Object> vars) {
        variables.putAll(vars);
    }


    /**
     * 获取变量值
     *
     * @param key 变量key
     * @return 变量值
     */
    public Object get(String key) {
        return variables.get(key);
    }

    /**
     * 获取变量值
     *
     * @param key   变量key
     * @param clazz 变量类型
     * @return 变量值
     */
    public <T> T get(String key, Class<T> clazz) {
        return MapUtil.get(variables, key, clazz);
    }

    /**
     * 获取节点结果
     *
     * @param nodeKey 节点key
     * @return 节点结果
     */
    public Object getNodeResult(String nodeKey) {
        return variables.get(NODE_PREFIX + nodeKey);
    }

    /**
     * 获取节点结果
     *
     * @param nodeKey 节点key
     * @param clazz   变量类型
     * @return 节点结果
     */
    public <T> T getNodeResult(String nodeKey, Class<T> clazz) {
        return MapUtil.get(variables, NODE_PREFIX + nodeKey, clazz);
    }

    /**
     * @return 所有流程变量
     */
    public Map<String, Object> getVariables() {
        return variables;
    }
}
