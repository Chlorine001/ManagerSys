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
 * 原则：msg 永远是可以直接展示给用户的文案
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /* ===== 1. 业务异常 ===== */
    @ExceptionHandler(BusinessException.class)
    public R<Object> handleBusiness(BusinessException e) {
        // 优先用异常里带的 msg，否则回退到枚举文案
        String msg = e.getMessage() != null
                ? e.getMessage()
                : ErrorCodes.getMessageByCode(e.getCode());

        logger.warn("业务异常 code={} msg={}", e.getCode(), msg);

        // 附加数据放进 data（可为 null）
        return R.fail(e.getCode(), msg, e.getData());
    }

    /* ===== 2. 主键 / 唯一索引冲突 → 9001 ===== */
    @ExceptionHandler(DuplicateKeyException.class)
    public R<Void> handleDuplicateKey(DuplicateKeyException e) {
        logger.error("主键/唯一约束冲突：", e);
        return R.fail(
                ErrorCodes.DUPLICATE_KEY.getCode(),
                ErrorCodes.DUPLICATE_KEY.getMessage()
        );
    }

    /* ===== 3. 其它数据完整性（非空、外键等） → 9002 ===== */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public R<Void> handleDataIntegrity(DataIntegrityViolationException e) {
        logger.error("数据完整性异常：", e);
        return R.fail(
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

    /* ===== 5. 兜底 → 9999（固定文案，绝不外泄原始异常信息） ===== */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        logger.error("系统异常：", e);   // 真实堆栈只进日志
        return R.fail(
                ErrorCodes.SYSTEM_ERROR.getCode(),
                ErrorCodes.SYSTEM_ERROR.getMessage()
        );
    }
}
