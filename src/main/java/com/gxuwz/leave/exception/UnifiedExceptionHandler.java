package com.gxuwz.leave.exception;

import com.gxuwz.leave.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Jzj
 * @Date 2021/12/17 20:08
 * @Version 1.0
 */
@Slf4j
@Component
@RestControllerAdvice //在controller层添加通知。如果使用@ControllerAdvice，则方法上需要添加@ResponseBody
public class UnifiedExceptionHandler {
    /**
     * 未定义异常
     */
    @ExceptionHandler(value = Exception.class) //当controller中抛出Exception，则捕获
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error();
    }
    /**
     * 自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e){
        log.error(e.getMessage(), e);
        return R.error().message(e.getMessage()).code(e.getCode());
    }


}
