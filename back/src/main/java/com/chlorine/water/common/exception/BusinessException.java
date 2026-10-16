package com.chlorine.water.common.exception;

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

    public BusinessException(int code) {
        this.code = code;
        this.data = null;
    }

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

    // ===== 自动取枚举里的默认文案 =====
    public BusinessException(ErrorCodes ec) {
        super(ec.getMessage());
        this.code = ec.getCode();
        this.data = null;
    }

    public BusinessException(ErrorCodes ec, String message) {
        super(message);
        this.code = ec.getCode();
        this.data = null;
    }

    public BusinessException(ErrorCodes ec, Object data) {
        super(ec.getMessage());
        this.code = ec.getCode();
        this.data = data;
    }

    public BusinessException(ErrorCodes ec, String message, Object data) {
        super(message);
        this.code = ec.getCode();
        this.data = data;
    }
}