package com.chlorine.water.common.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chlorine
 * @version 1.0
 * @Date 2026-10-15
 * @Description R 只做传输 —— code / ok / msg / data，不掺任何业务判断字段
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;      // 200 成功，500 失败
    private Boolean ok;        // 是否成功
    private String msg;    // 提示信息
    private T data;        // 真正的业务数据


    public R() {}

    public R(Boolean ok, int code, String msg, T data) {
        this.ok = ok;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /* ===== 成功 ===== */
    public static <T> R<T> ok() {
        return new R<>(true, 200, "成功", null);
    }


    public static <T> R<T> ok(T data) {
        return new R<>(true, 200, "成功", data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<>(true, 200, msg, data);
    }

    /* ===== 失败 ===== */
    public static <T> R<T> fail(String msg) {
        return new R<>(false, 500, msg, null);
    }
    public static <T> R<T> fail(int code, String msg) {
        return new R<>(false, code, msg, null);
    }
    public static <T> R<T> fail(int code, String msg, T data) {
        return new R<>(false, code, msg, data);
    }
}