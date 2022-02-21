package com.yinxiaoer.fastflow.core.build;

import com.yinxiaoer.fastflow.core.engine.ChildNode;
import com.yinxiaoer.fastflow.core.entity.FlowLink;
import com.yinxiaoer.fastflow.core.entity.FlowNode;
import com.yinxiaoer.fastflow.core.enums.DefaultNode;
import com.yinxiaoer.fastflow.core.enums.NodeType;

import java.util.*;

/**
 * 流程构建类
 *
 * @author yinxiuhe
 * @date 2021/11/19 16:27
 */
public class FlowBuilder {

    /**
     * 流程定义
     */
    private FlowDefine flowDefine;
    /**
     * 当前节点
     */
    private String currentNode;
    /**
     * 流程链接列表
     */
    private HashSet<FlowLink> flowLinks = new HashSet<>(16);
    /**
     * 节点Map
     */
    private Map<String, FlowNode> nodeMap = new HashMap<>(16);
    /**
     * 节点计数map
     */
    private Map<String, Integer> nodeCount = new HashMap<>(16);

    private FlowBuilder(String id) {
        this(id, null);
    }

    private FlowBuilder(String id, String name) {
        this.flowDefine = new FlowDefine(id, name);
        this.currentNode = DefaultNode.START.name();
        this.nodeMap.put(DefaultNode.START.name(), new FlowNode(DefaultNode.START));
    }

    /**
     * 开始构建
     *
     * @param id 流程id
     */
    public static FlowBuilder builder(String id) {
        return new FlowBuilder(id);
    }

    /**
     * 开始构建
     *
     * @param id   流程id
     * @param name 流程名称
     */
    public static FlowBuilder builder(String id, String name) {
        return new FlowBuilder(id, name);
    }

    /**
     * 构建完成
     *
     * @return 流程定义
     */
    public FlowDefine build() {
        target(DefaultNode.END.name());
        buildFlowDefine();
        return this.flowDefine;
    }

    /**
     * 构建流程定义
     */
    private void buildFlowDefine() {
        Map<String, List<ChildNode>> flowTreeMap = new HashMap<>(16);
        Map<String, List<String>> collectMap = new HashMap<>(16);

        for (FlowLink flowLink : this.flowLinks) {
            //处理树节点
            List<ChildNode> childNodes = flowTreeMap.get(flowLink.getSource());
            if (childNodes == null) {
                childNodes = new ArrayList<>(4);
            }
            childNodes.add(new ChildNode(flowLink.getCondition(), this.nodeMap.get(flowLink.getTarget())));
            flowTreeMap.put(flowLink.getSource(), childNodes);

            //处理归集节点
            List<String> collectNodes = collectMap.get(flowLink.getTarget());
            if (collectNodes == null) {
                collectNodes = new ArrayList<>(4);
            }
            collectNodes.add(flowLink.getSource());
            collectMap.put(flowLink.getTarget(), collectNodes);
        }
        //多个源节点指向一个目标节点则该目标节点为归集节点
        HashSet<String> collectNodes = new HashSet<>();
        for (Map.Entry<String, List<String>> entry : collectMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                collectNodes.add(entry.getKey());
            }
        }
        this.flowDefine.setFlowTreeMap(flowTreeMap);
        this.flowDefine.setCollectNodes(collectNodes);
        this.flowDefine.setNodeMap(this.nodeMap);
    }

    /**
     * 指定源节点
     *
     * @param nodeKey 源节点key
     */
    public FlowBuilder source(String nodeKey) {
        this.currentNode = nodeKey;
        return this;
    }

    /**
     * 指定并行源节点
     *
     * @param nodeKey 源节点key
     */
    public FlowBuilder parallelSource(String nodeKey) {
        this.currentNode = nodeKey;
        FlowNode flowNode = this.nodeMap.get(nodeKey);
        flowNode.setType(NodeType.PARALLEL.getCode());
        return this;
    }

    /**
     * 指定目标节点
     *
     * @param nodeKey 目标节点key
     */
    public FlowBuilder target(String nodeKey) {
        return target(nodeKey, null);
    }

    /**
     * 指定目标节点
     *
     * @param nodeKey  目标节点key
     * @param executor 执行器
     */
    public FlowBuilder target(String nodeKey, String executor) {
        return target(nodeKey, executor, null);
    }

    /**
     * 指定目标节点
     *
     * @param nodeKey  目标节点key
     * @param executor 执行器
     */
    public FlowBuilder target(String nodeKey, String executor, String around) {
        //添加流程链接
        this.flowLinks.add(new FlowLink(this.currentNode, nodeKey));
        putNodeMap(nodeKey, executor, around);
        //将当前节点后移
        this.currentNode = nodeKey;
        return this;
    }

    /**
     * 指定条件目标节点
     *
     * @param nodeKey 目标节点key
     */
    public FlowBuilder conditionTarget(String nodeKey, String condition) {
        return conditionTarget(nodeKey, condition, null);
    }


    /**
     * 指定条件目标节点
     *
     * @param nodeKey  目标节点key
     * @param executor 执行器
     */
    public FlowBuilder conditionTarget(String nodeKey, String condition, String executor) {
        return conditionTarget(nodeKey, condition, executor, null);
    }

    /**
     * 指定条件目标节点
     *
     * @param nodeKey  目标节点key
     * @param executor 执行器
     */
    public FlowBuilder conditionTarget(String nodeKey, String condition, String executor, String around) {
        //添加流程链接
        this.flowLinks.add(new FlowLink(this.currentNode, nodeKey, condition));
        putNodeMap(nodeKey, executor, around);
        //将当前节点后移
        this.currentNode = nodeKey;
        return this;
    }

    /**
     * 放入节点map
     *
     * @param nodeKey  节点key
     * @param executor 执行着
     * @param around   环绕处理方法
     */
    private void putNodeMap(String nodeKey, String executor, String around) {
        FlowNode flowNode = new FlowNode(nodeKey, executor, around);
        flowNode.setX(nodeCount());
        this.nodeMap.put(nodeKey, flowNode);
    }

    /**
     * 指定归集节点
     *
     * @param nodeKey 归集节点key
     */
    public FlowBuilder collect(String nodeKey) {
        target(nodeKey);
        return this;
    }

    /**
     * 节点计数， 用于确定子节点顺序
     */
    private Integer nodeCount() {
        Integer count = this.nodeCount.get(this.currentNode);
        if (count == null) {
            count = 0;
        }
        count++;
        this.nodeCount.put(this.currentNode, count);
        return count;
    }

}
