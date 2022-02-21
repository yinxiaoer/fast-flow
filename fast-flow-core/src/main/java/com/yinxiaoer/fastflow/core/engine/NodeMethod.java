package com.yinxiaoer.fastflow.core.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 节点方法
 *
 * @author yinxiuhe
 * @date 2021/11/19 16:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeMethod implements Serializable {

    /**
     * 节点key
     */
    private String key;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点服务
     */
    private Object service;
    /**
     * 节点方法
     */
    private Method method;

}
