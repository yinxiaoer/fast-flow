package com.yinxiaoer.fastflow.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 流程节点方法
 *
 * @author yinxiuhe
 * @date 2021/11/19 16:27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowNodeMethod {

    /**
     * 节点key
     */
    String value();

    /**
     * 节点名称
     */
    String name() default "";

}
