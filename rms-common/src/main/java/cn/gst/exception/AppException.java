package cn.gst.exception;

import lombok.Data;

/**
 * 系统异常类
 */
@Data
public class AppException extends RuntimeException{

    private Integer code = 500;
    private String msg = "服务器内部错误";

    public AppException(AppExceptionCodeMsg appExceptionCodeMsg){
        this.code = appExceptionCodeMsg.getCode();
        this.msg = appExceptionCodeMsg.getMsg();
    }

    public AppException(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

}