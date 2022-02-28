package com.yinxiaoer.fast.flow.build;

import cn.hutool.core.collection.CollUtil;
import com.yinxiaoer.fast.flow.context.FlowNode;
import com.yinxiaoer.fast.flow.enums.NodeType;
import lombok.Data;

import java.util.*;

/**
 * 流程定义
 *
 * @author yinxiuhe
 * @date 2021/3/24 15:05
 */
@Data
public class FlowDefine {

    /**
     * 流程id
     */
    private String id;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 普通节点关系map,key=上一节点，value=子节点列表
     */
    private Map<String, List<ChildNode>> flowTreeMap = new HashMap<>();

    /**
     * 归集节点集合
     */
    private HashSet<String> collectNodes = new HashSet<>();

    /**
     * 节点Map
     */
    private Map<String, FlowNode> nodeMap = new HashMap<>(16);

    public FlowDefine(String id) {
        this(id, null);
    }

    public FlowDefine(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @param nodeKey 节点key
     * @return 节点信息
     */
    public FlowNode getNode(String nodeKey){
        return nodeMap.get(nodeKey);
    }

    /**
     * @param nodeKey 节点key
     * @return 是否为并行节点
     */
    public boolean isParallelNode(String nodeKey){
        return NodeType.PARALLEL.getCode() == nodeMap.get(nodeKey).getType();
    }

    /**
     * 获取子节点
     * @param nodeKey 节点key
     * @return 子节点列表
     */
    public List<ChildNode> getChildNode(String nodeKey){
        List<ChildNode> childNodes = flowTreeMap.get(nodeKey);
        if(childNodes == null){
            return null;
        }
        return CollUtil.sort(childNodes, Comparator.comparing(ChildNode::getX));
    }

    /**
     * @param nodeKey 节点key
     * @return 是否为归集节点
     */
    public boolean isCollectNode(String nodeKey){
        return collectNodes.contains(nodeKey);
    }
}
