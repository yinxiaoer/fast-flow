package com.yinxiaoer.fastflow.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 流程链接
 *
 * @author yinxiuhe
 * @date 2021/11/19 14:44
 */
@Data
@NoArgsConstructor
@TableName("fast_flow_link")
public class FlowLink {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 流程key
     */
    private String flowKey;
    /**
     * 链接来源节点key
     */
    private String source;
    /**
     * 链接目标节点key
     */
    private String target;
    /**
     * 条件
     */
    private String condition;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public FlowLink(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public FlowLink(String source, String target, String condition) {
        this.source = source;
        this.target = target;
        this.condition = condition;
    }

}
