package com.f.demo.common.exception;

/**
 * 业务异常类
 * Created by tang.shi on 2019/01/08.
 */
public class BusinessException extends BaseException {

    private int code;
    private String message;

    public BusinessException(int code,String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 异常构建方法
     *
     * @param message
     */
    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
