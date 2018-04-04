package com.yuanshanbao.common.processor;

/**
 * 实现此接口的类可以得到代理类
 */
public interface BeanSelfAware {
    void setSelf(Object proxyBean);
}