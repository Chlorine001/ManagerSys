package com.chlorine.water.config.Error;

import lombok.Getter;

/**
 * 错误码枚举
 * 统一管理系统所有错误码，避免重复维护
 */
@Getter
public enum ErrorCodes {
    // 用户相关错误 (1001-1999)
    USER_NOT_FOUND(1001, "用户未找到"),
    USER_ALREADY_FOUND(1002, "用户已存在"),
    USER_NOT_LOGIN(1003, "用户未登录"),
    USER_NOT_AUTHORIZED(1004, "用户未授权"),
    USER_NOT_ENABLED(1005, "用户未启用"),

    // 订单相关错误 (2001-2999)
    ORDER_ALREADY_PAID(2001, "订单已支付"),
    ;

    private final int code;
    private final String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据错误码获取枚举
     */
    public static ErrorCodes fromCode(int code) {
        for (ErrorCodes error : values()) {
            if (error.getCode() == code) {
                return error;
            }
        }
        return null;
    }

    /**
     * 获取错误码名称
     */
    public static String getNameByCode(int code) {
        ErrorCodes error = fromCode(code);
        return error != null ? error.name() : "UNKNOWN_ERROR";
    }

    /**
     * 获取错误信息
     */
    public static String getMessageByCode(int code) {
        ErrorCodes error = fromCode(code);
        return error != null ? error.getMessage() : "未知错误";
    }
}
