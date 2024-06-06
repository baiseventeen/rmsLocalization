package cn.gst.exception;

public enum AppExceptionCodeMsg {
    /**
     * 枚举错误类型
     * 针对常出现的异常情况
     * 常见的业务异常建议添加到此处
     * 编写对应的错误码与错误信息，例：REQUEST_PARAM_EMPTY(10002,"请求参数为空");
     * 出现错误时，直接使用该类对应错误类型即可
     * @author lzx
     */

    LOGIN_ERROR(10001,"账号或密码错误"),

    REQUEST_PARAM_EMPTY(10002,"请求参数为空"),

    PROJECT_NOT_EXIST(20001, "项目不存在"),

    SYSTEM_SKILL_EXISTED(30001, "对应技术系统已存在");



    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    AppExceptionCodeMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}