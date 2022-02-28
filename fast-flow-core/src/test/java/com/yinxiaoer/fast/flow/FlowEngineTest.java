package com.yinxiaoer.fast.flow;

import com.yinxiaoer.fast.flow.build.FlowBuilder;
import com.yinxiaoer.fast.flow.build.FlowDefine;
import com.yinxiaoer.fast.flow.context.FlowContext;
import com.yinxiaoer.fast.flow.context.FlowNode;
import com.yinxiaoer.fast.flow.function.FlowFunction;
import com.yinxiaoer.fast.flow.function.FlowVoidFunction;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FlowEngineTest {

    @Test
    public void test_execute(){
        FlowFunction func = (context, node) -> {System.out.println(node.getKey()+"_"+node.getName()); return null;};
        FlowVoidFunction voidFunc = (context, node) -> System.out.println(node.getKey()+"_"+node.getName());
        FlowNode node4 = new FlowNode("node4", func);
        FlowNode node8 = new FlowNode("node8", voidFunc);

        FlowDefine flowDefine = FlowBuilder.builder("test1", "测试流程1")
                .target("node1", func)
                .target("node2", voidFunc)
                .target("node3", func)
                .parallelSource("node3").target("node31", (context, node) -> {
                    System.out.println("node31==========start");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("node31==========end");
                    return "node3Result";
                }).target("node32", func).target("node33", func).target(node4)
                .parallelSource("node3").target("node34", func).target(node4)
                .parallelSource("node3").target("node35", func).target(node4)
                .target("node5", func)
                .source("node5").conditionTarget("node6", context -> "张三1".equals(context.get("name")), func).target(node8)
                .source("node5").conditionTarget("node7", context -> "张三2".equals(context.get("name", String.class)), func).target(node8)
                .build();

        FlowEngine flowEngine = new FlowEngine();
        Map<String, Object> vars = new HashMap<>();
        vars.put("name", "张三1");
        FlowContext execute = flowEngine.execute(flowDefine, vars);
        System.out.println(execute);
    }

}
