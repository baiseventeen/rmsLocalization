package cn.gst.controller;

import cn.gst.domain.Developer;
import cn.gst.domain.Project;
import cn.gst.exception.AppExceptionCodeMsg;
import cn.gst.feignclient.DeveloperFeignClient;
import cn.gst.query.DeveloperQuery;
import cn.gst.query.ProjectHistroyQuery;
import cn.gst.query.ProjectQuery;
import cn.gst.service.ProjectHistoryService;
import cn.gst.service.ProjectService;
import cn.gst.vo.PageBean;
import cn.gst.vo.ResultBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectHistoryService projectHistoryService;
    @Autowired
    DeveloperFeignClient developerFeignClient;

    /**
     * 通过id获取project对象
     * @param id
     * @return 具体某个项目
     */
    @GetMapping("/{id}")
    public ResultBean getProjectById(@PathVariable("id") String id){
        Project project = projectService.findById(id);
        if(project == null){
            return ResultBean.error(AppExceptionCodeMsg.PROJECT_NOT_EXIST);
        }
        return ResultBean.success(project);
    }

    /**
     * 通过projectQuery条件查询项目列表
     * @param projectQuery
     * @return 项目列表
     */
    @PostMapping("/getProjectListBySearch")
    public ResultBean getProjectListBySearch(@RequestBody ProjectQuery projectQuery){
        PageBean<Project> projects = projectService.findBySeacrh(projectQuery);
        for(Project project: projects.getRowData()){
            String responsiblePersonId = project.getResponsiblePersonId();
            DeveloperQuery query = new DeveloperQuery();
            query.setId(responsiblePersonId);
            ResultBean bean = developerFeignClient.page(query);
            PageBean<Developer> pageBean = (PageBean<Developer>) bean.getData();
            List<Developer> list = pageBean.getRowData();
            Developer developer = list.get(0);
            project.setResponsiblePersonName(developer.getName());
        }
        return ResultBean.success(projects);
    }

    /**
     * 保存 新增 或更新 后的项目
     * @param project
     * @return
     */
    @PostMapping
    public ResultBean save(@RequestBody Project project){
        if(project.getId() == null){
            projectService.add(project);
        } else {
            // 记录并更新
            projectService.recordAndUpdate(project);
        }
        return ResultBean.success();
    }

    /**
     * 通过id删除某个项目以及下属系统
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultBean deleteProject(@PathVariable("id") String id){
        projectService.deleteProject(id);
        return ResultBean.success();
    }

    /**
     * 通过项目id获得该项目的历史版本
     * @param projectHistroyQuery
     * @return 返回历史记录列表
     */
    @PostMapping("/getProjectHistoryListByProjectId")
    public ResultBean getProjectListByProjectId(@RequestBody ProjectHistroyQuery projectHistroyQuery){
        PageBean<Project> projectHistorys = projectHistoryService.findByProjectId(projectHistroyQuery);
        return ResultBean.success(projectHistorys);
    }

    /**
     * 通过项目id和版本号获得该项目的唯一历史版本
     * @param projectHistroyQuery
     * @return 返回唯一历史记录
     */
    @PostMapping("/getProjectHistoryByProjectIdAndVersion")
    public ResultBean getProjectHistoryByProjectIdAndVersion(@RequestBody ProjectHistroyQuery projectHistroyQuery){
        Project projectHistory = projectHistoryService.findByProjectIdAndVersion(projectHistroyQuery);
        return ResultBean.success(projectHistory);
    }

    /**
     * 项目恢复，下属系统也需恢复
     * @param map
     * @return
     */
    @PostMapping("/restore")
    public ResultBean restore(@RequestBody Map map){
        String projectId = (String) map.get("projectId");
        String version = (String) map.get("version");
        projectHistoryService.restore(projectId, version);
        return ResultBean.success();
    }

}
