package com.ffysVideo.handler;

import com.ffysVideo.result.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResultData exceptionHandler(RuntimeException exception) {
        log.info("异常信息：{}", exception.getMessage());
        return ResultData.error(exception.getMessage());
    }

}
