package cn.gst.controller;

import cn.gst.exception.AppException;
import cn.gst.exception.AppExceptionCodeMsg;
import cn.gst.service.LoginService;
import cn.gst.util.JwtUtil;
import cn.gst.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 登陆模块
 * @author lzx
 */

@RestController
@RequestMapping(value = "/login",produces = "application/json;charset=utf-8")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping
    public ResultBean<String> login(@RequestBody HashMap<String,Object> loginInfo){
        if(loginInfo==null||loginInfo.get("account")==null||loginInfo.get("password")==null){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }{
            String staff_id = String.valueOf(loginInfo.get("account"));
            String password = String.valueOf(loginInfo.get("password"));
            Map<String,String> resultMap = loginService.findById(staff_id);
            if(resultMap!=null&&resultMap.get("password").equals(password)){
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String token = JwtUtil.createJWT(uuid);
                Map<String,Object> data = new HashMap<>();
                data.put("role",resultMap.get("role"));
                data.put("developerName",resultMap.get("developer_name"));
                data.put("developerId",resultMap.get("developer_id"));
                data.put("token",token);
                return ResultBean.success(data);
            }
        }
        return ResultBean.error(AppExceptionCodeMsg.LOGIN_ERROR);
    }
}
