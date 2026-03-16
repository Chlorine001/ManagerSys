package com.chlorine.water.config.Error;
import java.util.HashMap;
import java.util.Map;

// 定义错误码常量或枚举

public final class ErrorCodes {
    public static final int USER_NOT_FOUND = 1001;
    public static final int USER_ALREADY_FOUND = 1002;
    public static final int ORDER_ALREADY_PAID = 2001;
    // ...

    private static final Map<Integer, String> CODE_TO_NAME = new HashMap<>();

    static {
        CODE_TO_NAME.put(USER_NOT_FOUND, "USER_NOT_FOUND");
        CODE_TO_NAME.put(USER_ALREADY_FOUND, "USER_ALREADY_FOUND");
        CODE_TO_NAME.put(ORDER_ALREADY_PAID, "ORDER_ALREADY_PAID");
    }

    public static String getNameByCode(int code) {
        return CODE_TO_NAME.getOrDefault(code, "UNKNOWN_ERROR");
    }
}
