package com.f.demo.common.configration;

import com.f.demo.common.constant.ResultConstant;
import com.f.demo.common.model.dto.ResultDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by matf on 2020-04-18.
 */
@ControllerAdvice
public class RestResultHandler  implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String uri = serverHttpRequest.getURI().toString();
        if(uri.indexOf("/wechat/check")>=0 || uri.indexOf("/wechat/orderNotify")>=0 ||
                uri.indexOf("/wechat/refundNotify")>=0 || uri.indexOf("/swagger-resources")>=0 || uri.indexOf("/v2/api-docs")>=0){
            return body;
        }
        ResultDTO result = new ResultDTO();
        result.setCode(ResultConstant.RESULT_CODE_SUCCESS);
        result.setMessage("请求成功");
        if(body instanceof ResultDTO){
            result = (ResultDTO)body;
        }else{
            result.setData(body);
        }
        return result;
    }
}
