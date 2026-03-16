package com.chlorine.water.config.Error;

import lombok.Getter;

/**
 * @version 1.0
 * @Author chenglong
 * @className BusinessException
 * @Description
 * @Date 2026-03-16
 */
@Getter
public class BusinessException extends RuntimeException {
    private final int code;          // 错误码
    private final Object data;        // 可选的附加数据

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.data = null;
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.data = null;
    }

}

