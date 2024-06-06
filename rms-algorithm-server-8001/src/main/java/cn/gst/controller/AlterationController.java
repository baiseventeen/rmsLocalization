package cn.gst.controller;

import cn.gst.entity.Alteration;
import cn.gst.query.AlterationQuery;
import cn.gst.service.AlterationService;
import cn.gst.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 柏琪
 * @date 2024-05-20 16:50
 */
@RestController
@RequestMapping("/alteration")
public class AlterationController {
    @Autowired
    AlterationService alterationService;

    @PostMapping("/add")
    public ResultBean add(@RequestBody Alteration alteration){
        alterationService.add(alteration);
        return ResultBean.success();
    }

    @PostMapping("/update")
    public ResultBean update(@RequestBody Alteration alteration){
        alterationService.update(alteration);
        return ResultBean.success();
    }

    @PostMapping("/manager")
    public ResultBean findWithManager(@RequestBody AlterationQuery alterationQuery){
        return ResultBean.success(alterationService.findWithManager(alterationQuery));
    }

    @PostMapping("/admin")
    public ResultBean findWithAdmin(@RequestBody AlterationQuery alterationQuery){
        return ResultBean.success(alterationService.findWithAdmin(alterationQuery));
    }

    @PostMapping("/self")
    public ResultBean findByRequestDeveloperId(@RequestBody AlterationQuery alterationQuery){
        return ResultBean.success(alterationService.findByRequestDeveloperId(alterationQuery));
    }
}
