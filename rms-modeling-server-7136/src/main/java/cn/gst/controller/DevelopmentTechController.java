package cn.gst.controller;

import cn.gst.domain.DevelopmentTech;
import cn.gst.exception.AppException;
import cn.gst.exception.AppExceptionCodeMsg;
import cn.gst.service.DevelopmentTechService;
import cn.gst.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/developmentTechManage",produces = "application/json;charset=utf-8")
public class DevelopmentTechController {

    @Autowired
    DevelopmentTechService developmentTechService;

    @PostMapping("/add")
    public ResultBean add(@RequestBody DevelopmentTech developmentTech) {
        if(developmentTech==null||StringUtils.isEmpty(developmentTech.getName())){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        developmentTechService.add(developmentTech);
        return ResultBean.success(developmentTech.getId());
    }

    @GetMapping("/findAll")
    public ResultBean findAll(){
        return ResultBean.success(developmentTechService.findAll());
    }

}
