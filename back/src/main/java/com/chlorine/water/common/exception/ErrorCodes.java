package com.chlorine.water.common.exception;

import lombok.Getter;

/**
 * 错误码枚举
 * 统一管理系统所有错误码，避免重复维护
 */
@Getter
public enum ErrorCodes {

    // 用户相关 (1001-1999)
    USER_NOT_FOUND(1001, "用户未找到"),
    USER_ALREADY_FOUND(1002, "用户已存在"),
    USER_NOT_LOGIN(1003, "用户未登录"),
    USER_NOT_AUTHORIZED(1004, "用户未授权"),
    USER_NOT_ENABLED(1005, "用户未启用"),
    LOGIN_FAILED(1006, "账号或密码错误"),

    // 订单相关 (2001-2999)
    ORDER_ALREADY_PAID(2001, "订单已支付"),

    // 菜单相关 (3001-3999)
    // 菜单相关 (3001-3999)
    MENU_NOT_FOUND(3001, "菜单不存在"),
    MENU_PARENT_NOT_FOUND(3002, "父级菜单不存在"),
    MENU_URL_DUPLICATE(3003, "同级下已存在相同的路径"),
    MENU_HAS_CHILDREN(3004, "请先删除该菜单下的子菜单"),
    MENU_SELF_AS_PARENT(3005, "不能把自身设为父级"),
    MENU_PARENT_IS_DESCENDANT(3006, "不能把父级设为自己的下级"),

    // 参数 / 校验 (4001-4999)
    PARAM_INVALID(4001, "参数校验失败"),


    // 数据库相关 (9001-9999)
    DUPLICATE_KEY(9001, "数据已存在，请勿重复提交"),
    DATA_INTEGRITY_VIOLATION(9002, "数据完整性校验失败"),
    DB_ERROR(9003, "数据库操作失败"),

    // 系统级 (9998-9999)
    SYSTEM_ERROR(9999, "系统繁忙，请稍后重试"),
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
        for (ErrorCodes e : values()) {
            if (e.code == code) return e;
        }
        return null;
    }

    public static String getMessageByCode(int code) {
        ErrorCodes e = fromCode(code);
        return e != null ? e.getMessage() : SYSTEM_ERROR.getMessage();
    }
}
