package cn.gst.controller;

import cn.gst.service.GanttTaskService;
import cn.gst.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 计划展示
 * @author 柏琪
 * @date 2024-05-13 22:16
 */
@RestController
@RequestMapping("/display")
public class DisplayController {

    @Autowired
    GanttTaskService ganttTaskService;

    @GetMapping("/developer")
    public ResultBean getGanttTasksByDeveloper(){
        System.out.println("ok");
        return ResultBean.success(ganttTaskService.getDeveloperListWithGanttTasks());
    }

    @GetMapping("/system")
    public ResultBean getGanttTasksBySystem(){
        System.out.println("ok");
        return ResultBean.success(ganttTaskService.getSystemListWithGanttTasks());
    }

    @GetMapping("/project")
    public ResultBean getGanttTasksByProject(){
        System.out.println("ok");
        return ResultBean.success(ganttTaskService.getProjectListWithGanttTasks());
    }

}
