package com.blinkfox.release.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类.
 *
 * @author blinkfox on 2019-06-16.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一处理 Exception 的方法.
     *
     * @param e 异常实例
     * @return 异常提示的字符串
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("【异常处理】请求服务接口发生了异常，请检查!", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    /**
     * 统一处理 RunException 的方法.
     *
     * @param e 异常实例
     * @return 异常提示的字符串
     */
    @ExceptionHandler(RunException.class)
    public ResponseEntity<String> handleRunException(Exception e) {
        log.error("【异常处理】请求服务接口发生了运行时异常，请检查!", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
