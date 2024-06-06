package cn.gst.controller;

import cn.gst.param.ScheduleParams;
import cn.gst.service.AlgorithmService;
import cn.gst.service.MailService;
import cn.gst.service.ProjectSystemService;
import cn.gst.util.DateUtil;
import cn.gst.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 柏琪
 * @date 2024-05-06 17:18
 */
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {

    @Autowired
    AlgorithmService algorithmService;
    @Autowired
    ProjectSystemService projectSystemService;
    @Autowired
    MailService mailService;

    @PostMapping("/scheduleGenerate")
    public ResultBean ScheduleGenerate(@RequestBody HashMap<String,Object> paramsMap){
        // 存储算法所需参数
        ScheduleParams scheduleParams = new ScheduleParams();
        scheduleParams.setMaxDailyWorkSaturation(Double.valueOf(paramsMap.get("maxDailyWorkSaturation").toString()));
        scheduleParams.setMaxAverageWorkSaturation(Double.valueOf(paramsMap.get("maxAverageWorkSaturation").toString()));
        scheduleParams.setSystemIdList((ArrayList<String>)paramsMap.get("systemIdList"));
        scheduleParams.setDeveloperIdList((ArrayList<String>)paramsMap.get("developerIdList"));
//        System.out.println(DateUtil.string2localdate((String)paramsMap.get("startTime")));
        scheduleParams.setStartTime(DateUtil.string2localdate((String)paramsMap.get("startTime")));

        // 为所选系统进行当前时间的优先级评分
        projectSystemService.evaluate(scheduleParams.getSystemIdList());

        algorithmService.algorithm(scheduleParams);

        mailService.sendEMail();

        return ResultBean.success("计划生成成功");

    }
}
