package com.blinkfox.release.exception;

/**
 * 本服务自定义的运行时异常.
 *
 * @author blinkfox on 2019-06-16.
 */
public class RunException extends RuntimeException {

    /**
     * uid.
     */
    private static final long serialVersionUID = -3030387677806493215L;

    /**
     * 根据 Message 来构造异常实例.
     *
     * @param message 消息
     */
    public RunException(String message) {
        super(message);
    }

    /**
     * 根据 message 和 异常实例来构造异常实例.
     *
     * @param message 消息
     * @param e       Throwable实例
     */
    public RunException(String message, Throwable e) {
        super(message, e);
    }

}
