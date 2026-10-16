package com.chlorine.water.common.exception;

import com.chlorine.water.common.core.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器 —— 所有异常的最终出口
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /* ========== 1. 业务异常（最常用） ========== */
    @ExceptionHandler(BusinessException.class)
    public R<Object> handleBusiness(BusinessException e) {
        ErrorCodes ec = ErrorCodes.fromCode(e.getCode());
        String name = ec != null ? ec.name() : "UNKNOWN_ERROR";
        // 优先用异常里带的 msg，没有就用枚举默认
        String msg = e.getMessage() != null
                ? e.getMessage()
                : (ec != null ? ec.getMessage() : "业务异常");

        logger.warn("业务异常 code={} name={} msg={}", e.getCode(), name, msg);

        R<Object> r = R.failBusiness(e.getCode(), msg);
        r.setData(e.getData());   // 把附加数据带出去
        return r;
    }

    /* ========== 2. 主键/唯一索引冲突 → 9001 ========== */
    @ExceptionHandler(DuplicateKeyException.class)
    public R<Void> handleDuplicateKey(DuplicateKeyException e) {
        logger.error("主键/唯一约束冲突：", e);
        return R.failBusiness(
                ErrorCodes.DUPLICATE_KEY.getCode(),
                ErrorCodes.DUPLICATE_KEY.getMessage()
        );
    }

    /* ========== 3. 其它数据完整性（非空、外键等） → 9002 ========== */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public R<Void> handleDataIntegrity(DataIntegrityViolationException e) {
        logger.error("数据完整性异常：", e);
        return R.failBusiness(
                ErrorCodes.DATA_INTEGRITY_VIOLATION.getCode(),
                ErrorCodes.DATA_INTEGRITY_VIOLATION.getMessage()
        );
    }

    /* ========== 4. @Valid 参数校验 → 4001 ========== */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : ErrorCodes.PARAM_INVALID.getMessage();
        logger.warn("参数校验失败：{}", msg);
        return R.fail(ErrorCodes.PARAM_INVALID.getCode(), msg);
    }

    /* ========== 5. 兜底：其它所有异常 → 9999 ========== */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        logger.error("系统异常：", e);
        String msg = e.getMessage() != null
                ? e.getMessage()
                : ErrorCodes.SYSTEM_ERROR.getMessage();
        return R.fail(ErrorCodes.SYSTEM_ERROR.getCode(), msg);
    }
}