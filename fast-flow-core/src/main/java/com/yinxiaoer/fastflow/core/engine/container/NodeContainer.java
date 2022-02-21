package com.yinxiaoer.fastflow.core.engine.container;

import cn.hutool.core.util.StrUtil;
import com.yinxiaoer.fastflow.core.api.NodeAround;
import com.yinxiaoer.fastflow.core.engine.NodeMethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 节点映射
 *
 * @author yinxiuhe
 * @date 2021/11/19 16:28
 */
public class NodeContainer {

    private final Map<String, NodeMethod> mapping = new ConcurrentHashMap<>(16);

    private final Map<String, NodeAround> aroundMapping = new ConcurrentHashMap<>(16);

    /**
     * 添加节点
     *
     * @param nodeKey    节点key
     * @param nodeMethod 节点方法
     */
    public void put(String nodeKey, NodeMethod nodeMethod) {
        mapping.put(nodeKey, nodeMethod);
    }

    /**
     * 获取节点
     *
     * @param nodeKey 节点key
     * @return 节点方法
     */
    public NodeMethod get(String nodeKey) {
        if (StrUtil.isBlank(nodeKey)) {
            return null;
        }
        return mapping.get(nodeKey);
    }

    /**
     * 添加around
     *
     * @param aroundKey  节点key
     * @param nodeMethod around类
     */
    public void putAround(String aroundKey, NodeAround nodeMethod) {
        aroundMapping.put(aroundKey, nodeMethod);
    }

    /**
     * 获取around
     *
     * @param aroundKey 节点key
     * @return around类
     */
    public NodeAround getAround(String aroundKey) {
        if (StrUtil.isBlank(aroundKey)) {
            return null;
        }
        return aroundMapping.get(aroundKey);
    }

}
