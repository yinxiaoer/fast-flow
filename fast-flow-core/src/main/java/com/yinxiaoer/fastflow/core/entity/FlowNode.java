package com.yinxiaoer.fastflow.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yinxiaoer.fastflow.core.enums.DefaultNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程节点
 *
 * @author yinxiuhe
 * @date 2021/11/19 14:44
 */
@Data
@NoArgsConstructor
@TableName("fast_flow_node")
public class FlowNode implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 流程key
     */
    private String flowKey;
    /**
     * 节点key
     */
    private String key;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private int type;
    /**
     * 执行者
     */
    private String executor;
    /**
     * 重试次数
     */
    private int retry;
    /**
     * 环绕处理方法
     */
    private String around;
    /**
     * 节点坐标
     */
    private int x;
    /**
     * 节点坐标
     */
    private int y;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public FlowNode(DefaultNode node) {
        this.key = node.getKey();
        this.name = node.getName();
    }

    public FlowNode(String key, String executor) {
        this.key = key;
        this.executor = executor;
    }

    public FlowNode(String key, String executor, String around) {
        this.key = key;
        this.executor = executor;
        this.around = around;
    }

}
