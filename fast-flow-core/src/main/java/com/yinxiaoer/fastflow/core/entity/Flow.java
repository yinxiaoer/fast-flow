package com.yinxiaoer.fastflow.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程配置表
 *
 * @author yinxiuhe
 * @date 2021/11/19 14:44
 */
@Data
@TableName("fast_flow")
public class Flow implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 流程key
     */
    private String key;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
