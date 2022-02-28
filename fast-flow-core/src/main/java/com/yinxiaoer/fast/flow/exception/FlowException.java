package com.yinxiaoer.fast.flow.exception;

/**
 * 流程异常
 *
 * @author yinxiuhe
 * @date 2022/2/28 15:13
 */
public class FlowException extends RuntimeException {

    public FlowException() {
    }

    public FlowException(String message) {
        super(message);
    }
}
