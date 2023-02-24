package com.dtzhejiang.irs.res.bill.adapter.handler;

import com.dtzhejiang.irs.res.bill.common.dto.Response;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response handle(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();
        StringBuilder errorMsg = new StringBuilder() ;

        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> errorMsg.append(error.getField()).append(" ").append(error.getDefaultMessage()).append("!"));
        }
        String errMsg = errorMsg.toString();
        log.warn("argument illegal,msg:{}", errMsg,exception);
        return  Response.buildFailure("400", errMsg);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Response handle(IllegalArgumentException exception) {
        log.warn("argument illegal,msg:{}",exception.getMessage(),exception);
        return Response.buildFailure("400",exception.getMessage());
    }



    @ExceptionHandler(BusinessException.class)
    public Response handle(BusinessException e) {
        log.warn("biz exception,msg:{},extraMsg:{}",e.getMessage(),e.getExtraMsg());
        return Response.buildFailure("400",e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response handle(Exception exception) {
        log.warn("unknown error,msg:{}",exception.getMessage(),exception);
        return Response.buildFailure("500",exception.getMessage());
    }


}
