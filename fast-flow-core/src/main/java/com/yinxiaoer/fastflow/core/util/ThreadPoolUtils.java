package com.yinxiaoer.fastflow.core.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 线程池工具类
 *
 * @author yinxiuhe
 * @date 2021/11/19 16:29
 */
public class ThreadPoolUtils {

    /**
     * 构建队列
     *
     * @param size 队列大小
     * @return 队列
     */
    public static BlockingQueue<Runnable> buildQueue(int size) {
        return buildQueue(size, false);
    }

    /**
     * 构建队列
     *
     * @param size       队列大小
     * @param isPriority 是否优先级队列
     * @return 队列
     */
    public static BlockingQueue<Runnable> buildQueue(int size, boolean isPriority) {
        BlockingQueue<Runnable> queue;
        // 默认无队列
        if (size == 0) {
            queue = new SynchronousQueue<Runnable>();
        } else {
            // 有限队列或无限队列
            if (isPriority) {
                queue = size < 0 ? new PriorityBlockingQueue<Runnable>()
                        : new PriorityBlockingQueue<Runnable>(size);
            } else {
                queue = size < 0 ? new LinkedBlockingQueue<Runnable>()
                        : new LinkedBlockingQueue<Runnable>(size);
            }
        }
        return queue;
    }
}
