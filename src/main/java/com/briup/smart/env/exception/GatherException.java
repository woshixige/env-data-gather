package com.briup.smart.env.exception;

/**
 * 自定义的业务异常，当采集数据中发生异常，抛出当前异常对象
 */
public class GatherException extends RuntimeException{
    public GatherException(String message) {
        super(message);
    }
}
