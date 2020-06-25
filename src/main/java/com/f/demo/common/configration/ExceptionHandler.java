package com.f.demo.common.configration;

import com.f.demo.common.constant.ResultConstant;
import com.f.demo.common.constant.ResultMsgConstant;
import com.f.demo.common.exception.BusinessException;
import com.f.demo.common.model.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultDTO jsonErrorHandler(HttpServletRequest req, Exception e) {
        ResultDTO result = new ResultDTO();
        if(e instanceof BusinessException){
            BusinessException e1 = (BusinessException)e;
            result.setCode(e1.getCode());
            result.setMessage(e1.getMessage()==null? ResultMsgConstant.RESULT_MSG_ERROR:e1.getMessage());
        } else {
            result.setCode(ResultConstant.RESULT_CODE_ERROR);
            result.setMessage(ResultMsgConstant.RESULT_MSG_ERROR);
        }
        log.error("请求异常："+e.getMessage(),e);
        return result;
    }


}
