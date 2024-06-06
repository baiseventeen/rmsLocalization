package cn.gst.controller;

import cn.gst.domain.Developer;
import cn.gst.domain.ProjectSystem;
import cn.gst.exception.AppExceptionCodeMsg;
import cn.gst.feignclient.DeveloperFeignClient;
import cn.gst.query.DeveloperQuery;
import cn.gst.query.ProjectSystemHistroyQuery;
import cn.gst.query.ProjectSystemQuery;
import cn.gst.service.ProjectSystemHistoryService;
import cn.gst.service.ProjectSystemService;
import cn.gst.vo.PageBean;
import cn.gst.vo.ResultBean;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author 柏琪
 * @date 2024-04-12 16:40
 */
@RestController
@RequestMapping("/system")
public class ProjectSystemController {

    @Autowired
    ProjectSystemService projectSystemService;
    @Autowired
    ProjectSystemHistoryService projectSystemHistoryService;
    @Autowired
    DeveloperFeignClient developerFeignClient;

    /**
     * 通过system_id获取唯一系统
     */
    @GetMapping("/{id}")
    public ResultBean getProjectSystemById(@PathVariable("id") String systemId){
        ProjectSystem projectSystem = projectSystemService.findBySystemId(systemId);
        return ResultBean.success(projectSystem);
    }

    /**
     * 通过projectid来获取项目对应的系统列表，没有参数则查询全部
     */
    @PostMapping("/getProjectSystemListBySearch")
    public ResultBean getProjectSystemBySearch(@RequestBody ProjectSystemQuery projectSystemQuery){
        PageBean<ProjectSystem> projectSystemList = projectSystemService.findBySearch(projectSystemQuery);
        // 处理前端显示所需要的值
        for(ProjectSystem projectSystem: projectSystemList.getRowData()){
            String responsiblePersonId = projectSystem.getResponsiblePersonId();
            DeveloperQuery query = new DeveloperQuery();
            query.setId(responsiblePersonId);
            ResultBean bean = developerFeignClient.page(query);
            PageBean<Developer> pageBean = (PageBean<Developer>) bean.getData();
            List<Developer> list = pageBean.getRowData();
            Developer developer = list.get(0);
            projectSystem.setResponsiblePersonName(developer.getName());
        }
        return ResultBean.success(projectSystemList);
    }

    /**
     * 保存系统
     * 1. 若没有system则为新增
     *  1.1 若有同开发技术的system则返回已存在
     *  1.2 若没有则新增system
     * 2，若有system则为修改
     * @param projectSystem
     * @return
     */
    @PostMapping
    public ResultBean save(@RequestBody ProjectSystem projectSystem){
        if(projectSystem.getSystemId() == null){
            // 检测项目下系统类型是否已经存在
            if(projectSystemService.developmentTechIsExist(projectSystem.getProjectId(), projectSystem.getDevelopmentTech())){
                return ResultBean.error(AppExceptionCodeMsg.SYSTEM_SKILL_EXISTED);
            }
            projectSystemService.add(projectSystem);
        } else {
            projectSystemService.recordAndUpdate(projectSystem);
        }
        return ResultBean.success();
    }

    /**
     * 通过system_id删除系统，此时项目更新
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultBean deleteProjectSystem(@PathVariable("id") String id){
        projectSystemService.deleteById(id);
        return ResultBean.success();
    }

    @PostMapping("/getProjectSystemHistroyListBySearch")
    public ResultBean getProjectSystemListBySearch(@RequestBody ProjectSystemHistroyQuery projectSystemHistroyQuery){
        PageBean<ProjectSystem> projectSystemHistorys = projectSystemHistoryService.findBySearch(projectSystemHistroyQuery);
        return ResultBean.success(projectSystemHistorys);
    }

    @PostMapping("/getProjectSystemHistroyById")
    public ResultBean getProjectSystemHistoryById(@Param("id") String id){
        ProjectSystem projectSystem = projectSystemHistoryService.findById(id);
        return ResultBean.success(projectSystem);
    }
}
