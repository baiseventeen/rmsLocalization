package cn.gst.controller;

import cn.gst.entity.Investment;
import cn.gst.service.InvestmentService;
import cn.gst.service.MailService;
import cn.gst.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 进度管理模块
 * @author 柏琪
 * @date 2024-05-13 22:07
 */
@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    InvestmentService investmentService;
    @Autowired
    MailService mailService;

    @GetMapping("/project/{id}")
    public ResultBean projectProgressTracking(@PathVariable("id") String projectId){
        return ResultBean.success("TODO");
    }

    @GetMapping("/system/{id}")
    public ResultBean systemProgressTracking(@PathVariable("id") String systemId){
        return ResultBean.success("TODO");
    }

    @PostMapping
    public ResultBean findInvestment(@RequestBody HashMap<String,Object> info){
        String developerId = "";
        if(info.get("developerId") != null){
            developerId = (String) info.get("developerId");
        }
        List<Investment> investmentList = investmentService.fingBySearch(developerId);
        return ResultBean.success(investmentList);
    }

    @GetMapping("/sendEMail")
    public ResultBean sendEMail(){
        mailService.sendEMail();
        return ResultBean.success();
    }
}
