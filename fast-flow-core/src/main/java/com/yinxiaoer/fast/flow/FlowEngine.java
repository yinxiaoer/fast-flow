package com.yinxiaoer.fast.flow;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.SystemClock;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.StrUtil;
import com.yinxiaoer.fast.flow.build.ChildNode;
import com.yinxiaoer.fast.flow.build.FlowDefine;
import com.yinxiaoer.fast.flow.context.FlowContext;
import com.yinxiaoer.fast.flow.context.FlowNode;
import com.yinxiaoer.fast.flow.enums.DefaultNode;
import com.yinxiaoer.fast.flow.exception.FlowException;
import com.yinxiaoer.fast.flow.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 流程引擎
 *
 * @author yinxiuhe
 * @date 2021/3/25 17:49
 */
@Slf4j
public class FlowEngine {

    /**
     * 核心线程池
     */
    private int corePoolSize = 8;
    /**
     * 最大线程池
     */
    private int maximumPoolSize = 100;
    /**
     * 线程回收时间（毫秒）
     */
    private int keepAliveTime = 300000;
    /**
     * 队列大小，0为无界队列
     */
    private int queueSize = 0;
    /**
     * 线程名字
     */
    private String threadPoolName = "fast-flow";

    private final ThreadPoolExecutor flowThreadPool;

    public FlowEngine() {
        flowThreadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                ThreadPoolUtil.buildQueue(queueSize), new NamedThreadFactory(threadPoolName, false));
    }


    /**
     * 执行流程
     *
     * @param flowDefine 流程定义
     * @param vars       流程变量
     */
    public FlowContext execute(FlowDefine flowDefine, Map<String, Object> vars) {
        log.info("开始执行流程：{}_{}", flowDefine.getId(), flowDefine.getName());
        FlowContext context = new FlowContext(vars);
        //循环执行
        whileExecute(flowDefine, context, DefaultNode.START.name(), false);
        return context;
    }

    /**
     * 循环执行流程节点
     *
     * @param flowDefine        流程定义
     * @param context       流程上下文
     * @param startNode         开始执行节点
     * @param isParallelExecute 是否并行执行
     */
    private List<ChildNode> whileExecute(FlowDefine flowDefine, FlowContext context, String startNode, boolean isParallelExecute) {
        AtomicReference<FlowNode> parentNode = new AtomicReference<>(flowDefine.getNode(startNode));
        AtomicReference<List<ChildNode>> childNodes = new AtomicReference<>(flowDefine.getChildNode(startNode));
        AtomicBoolean isParallel = new AtomicBoolean(flowDefine.isParallelNode(startNode));

        while (CollUtil.isNotEmpty(childNodes.get())) {
            List<ChildNode> executeNodes = getChildNodes(context, childNodes, isParallel);
            //目标节点为空表示未找到满足条件的目标节点
            if (CollUtil.isEmpty(executeNodes)) {
                log.warn(StrUtil.format("节点{}未找到满足条件的目标节点", parentNode.get().getKey()));
                return null;
            }

            if (isParallel.get() && executeNodes.size() > 1) {
                final CountDownLatch latch = new CountDownLatch(executeNodes.size());
                for (ChildNode targetNode : executeNodes) {
                    flowThreadPool.execute(() -> {
                        if (flowDefine.isCollectNode(targetNode.getNodeKey())) {
                            log.info("目标执行节点为归集节点，退出并行执行");
                            isParallel.set(false);
                            return;
                        }
                        //执行节点
                        executeNode(targetNode, context);
                        //各线程并行循环往下执行
                        List<ChildNode> parallelChildNodes = whileExecute(flowDefine, context, targetNode.getNodeKey(), true);
                        //将父子节点设置到变量中，下一循环使用
                        parentNode.set(targetNode.getNode());
                        childNodes.set(parallelChildNodes);
                        latch.countDown();
                    });
                }
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                //非并行节点只执行第一个子节点
                ChildNode targetNode = executeNodes.get(0);
                if (isParallelExecute && flowDefine.isCollectNode(targetNode.getNodeKey())) {
                    log.info("目标执行节点为归集节点，退出并行执行");
                    isParallel.set(false);
                    return executeNodes;
                }
                //执行节点
                executeNode(targetNode, context);
                //将父子节点设置到变量中，下一循环使用
                parentNode.set(targetNode.getNode());
                childNodes.set(flowDefine.getChildNode(targetNode.getNodeKey()));
                //如果是并行节点，则将并行执行标志位置为true
                isParallel.set(flowDefine.isParallelNode(targetNode.getNodeKey()));
            }
        }
        return null;
    }

    /**
     * 获取满足条件的节点
     *
     * @param context 流程上下文
     * @param childNodes  子节点列表
     * @param isParallel  是否并行
     * @return 待执行的节点列表
     */
    private List<ChildNode> getChildNodes(FlowContext context, AtomicReference<List<ChildNode>> childNodes, AtomicBoolean isParallel) {
        //循环选择满足条件的节点
        List<ChildNode> executeNodes = new ArrayList<>(childNodes.get().size());
        for (ChildNode node : childNodes.get()) {
            //条件为空则默认成立
            if (node.getCondition() == null || node.getCondition().test(context)) {
                executeNodes.add(node);
                if (!isParallel.get()) {
                    break;
                }
            }
        }
        return executeNodes;
    }

    /**
     * 执行节点
     *
     * @param targetNode  目标执行节点
     * @param context 流程上下文
     */
    private void executeNode(ChildNode targetNode, FlowContext context) {
        FlowNode node = targetNode.getNode();
        log.info("执行{}_{}节点", node.getKey(), node.getName());
        //执行节点方法
        executeNodeMethod(context, node);
    }

    /**
     * 执行节点方法
     *
     * @param context 流程上下文
     * @param node        节点
     */
    private void executeNodeMethod(FlowContext context, FlowNode node) {
        if (node.getFunction() == null) {
            //节点为空不执行直接跳过
            return;
        }
        try {
            log.info("开始执行{}_{}节点方法", node.getKey(), node.getName());
            long startTime = SystemClock.now();
            Object returnValue = node.getFunction().apply(context, node);
            long endTime = SystemClock.now();
            log.info("结束执行{}_{}节点方法，执行时间：{}ms，执行结果：{}", node.getKey(), node.getName(), (endTime - startTime), returnValue);
            //将结果放入流程上下文中，name为流程key
            context.putNodeResult(node.getKey(), returnValue);
        } catch (Exception e) {
            log.error("执行{}_{}节点失败：{}", node.getKey(), node.getName(), e.getMessage(), e);
            throw new FlowException(StrUtil.format("执行{}节点失败：{}", node.getKey(), e.getMessage()));
        }
    }

}
