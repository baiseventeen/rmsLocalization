package cn.gst.exception;

import cn.gst.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * 异常拦截处理
 * 业务处理过程中，判断到出现业务异常，直接throw出一个AppException即可交由该类处理，无须try catch
 * @author lzx
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class}) //拦截异常类，出现异常就进入该方法
    @ResponseBody
    public <T> ResultBean exceptionHandler(Exception e){
        //判断拦截到的异常是不是我们自己定义的异常类型，若为程序自身异常，用自己的异常处理，给出对应错误码，错误信息
        if(e instanceof AppException){
            AppException appException = (AppException) e;
            return ResultBean.error(appException.getCode(),appException.getMsg());
        }
        e.printStackTrace();
        log.error(Arrays.toString(e.getStackTrace()));
        //底层严重异常，给出服务器异常信息
        return ResultBean.error(500,"请联系管理员\n"+e.getMessage());
    }

}