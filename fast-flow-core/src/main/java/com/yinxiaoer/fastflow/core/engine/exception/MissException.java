package com.yinxiaoer.fastflow.core.engine.exception;

/**
 * 未命中规则异常，用于未找到下一节点抛出
 * @author yinxiuhe
 * @date 2021/3/29 16:46
 */
public class MissException extends RuntimeException {

    public MissException() {
    }

    public MissException(String message) {
        super(message);
    }
}
