package cn.gst.vo;

import cn.gst.exception.AppExceptionCodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean<T>{

    private Integer code = 200; //返回码 默认为成功（200） 错误码根据异常情况返回对应错误码
    private String msg = "success";//返回信息 默认为success 错误返回错误信息
    private T data;


    public static <T> ResultBean success(String msg, T data){
        return new ResultBean(200,msg,data);
    }

    public static <T> ResultBean success(T data){
        return new ResultBean(200,"success",data);
    }

    public static <T> ResultBean success(){
        return new ResultBean(200,"success",null);
    }

    public static <T> ResultBean error(){
        return new ResultBean(500,"服务器内部异常",null);
    }

    public static <T> ResultBean error(Integer code,String msg){
        return new ResultBean(code,msg,null);
    }

    public static <T> ResultBean error(AppExceptionCodeMsg appExceptionCodeMsg){
        return new ResultBean(appExceptionCodeMsg.getCode(),appExceptionCodeMsg.getMsg(),null);
    }

}
