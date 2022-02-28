package com.yinxiaoer.fast.flow.context;

import com.yinxiaoer.fast.flow.function.FlowCondition;
import lombok.Data;

import java.io.Serializable;

/**
 * 流程连接
 *
 * @author yinxiuhe
 * @date 2022/2/28 11:00
 */
@Data
public class FlowLink implements Serializable {

    /**
     * 链连来源节点key
     */
    private String source;
    /**
     * 链连目标节点key
     */
    private String target;
    /**
     * 条件
     */
    private FlowCondition condition;

    public FlowLink() {
    }

    public FlowLink(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public FlowLink(String source, String target, FlowCondition condition) {
        this.source = source;
        this.target = target;
        this.condition = condition;
    }

}
