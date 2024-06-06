package cn.gst.service;

import cn.gst.entity.*;
import cn.gst.gantt.*;
import cn.gst.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * @author 柏琪
 * @date 2024-05-14 15:28
 */
@Service
public class GanttTaskService {
    @Autowired
    AlgorithmSchedulingService algorithmSchedulingService;
    @Autowired
    AlgorithmSchedulingPeriodService algorithmSchedulingPeriodService;
    @Autowired
    GanttService ganttService;

    public List<DeveloperGantt> getDeveloperListWithGanttTasks(){
        // 新建开发人员甘特图任务列表，用于返回
        // 使用map是为了方便按名字查找
        Map<String, DeveloperGantt> developerGanttMap= new HashMap<>();
        List<DeveloperGantt> developerGanttList = new ArrayList<>();
        // 获得计划所有项
        List<Scheduling> schedulingList = algorithmSchedulingService.findAll();

        for(int i = 0; i < schedulingList.size(); i++){
            Scheduling scheduling = schedulingList.get(i);
            String developerId = scheduling.getDeveloperId();
            DeveloperGantt gantt= developerGanttMap.get(developerId);
            // 如果当前不存在该开发人员，则新增
            if(gantt == null){
                DeveloperGantt developerGantt = new DeveloperGantt();
                DeveloperGanttTask developerGanttTask = scheduling2DeveloperGanttTask(scheduling);
                List<DeveloperGanttTask> developerGanttTasks = new ArrayList<>();

                developerGantt.setDeveloperId(developerId);
                developerGantt.setDeveloperName(scheduling.getDeveloperName());
                developerGanttTasks.add(developerGanttTask);
                developerGantt.setGanttTasks(developerGanttTasks);
                developerGanttMap.put(developerId, developerGantt);
            } else {
                // 转换格式
                DeveloperGanttTask developerGanttTask = scheduling2DeveloperGanttTask(scheduling);
                // 装入当前开发人员的甘特图任务列表中
                List<DeveloperGanttTask> developerGanttTasks = gantt.getGanttTasks();
                developerGanttTasks.add(developerGanttTask);
                gantt.setGanttTasks(developerGanttTasks);
            }
        }

        // 最后将map中的数据放到list里
        developerGanttMap.forEach((key,value)->{
            developerGanttList.add(value);
        });

        return developerGanttList;
    }

    public DeveloperGanttTask scheduling2DeveloperGanttTask(Scheduling scheduling){
        DeveloperGanttTask developerGanttTask = new DeveloperGanttTask();

        developerGanttTask.setId(scheduling.getId());
        developerGanttTask.setText(scheduling.getSystemName());
        developerGanttTask.setSystemId(scheduling.getSystemId());
        developerGanttTask.setDeveloperName(scheduling.getDeveloperName());
        developerGanttTask.setDeveloperId(scheduling.getDeveloperId());
        developerGanttTask.setSaturation(scheduling.getSaturation());
        developerGanttTask.setWorkload(scheduling.getWorkload());
        developerGanttTask.setStartDate(scheduling.getStartTime());
        // 为了显示正确，需要将结束时间加一
        LocalDate newEndDate = DateUtil.date2LocalDate(scheduling.getEndTime()).plusDays(1);
        developerGanttTask.setEndDate(DateUtil.localDate2Date(newEndDate));

        return developerGanttTask;
    }

    public SystemGanttTask scheduling2SystemGanttTask(Scheduling scheduling){
        SystemGanttTask systemGanttTask = new SystemGanttTask();

        systemGanttTask.setId(scheduling.getId());
        systemGanttTask.setStartDate(scheduling.getStartTime());
        // 为了显示正确，需要将结束时间加一
        LocalDate newEndDate = DateUtil.date2LocalDate(scheduling.getEndTime()).plusDays(1);
        systemGanttTask.setEndDate(DateUtil.localDate2Date(newEndDate));
        systemGanttTask.setSystemName(scheduling.getSystemName());
        systemGanttTask.setSystemId(scheduling.getSystemId());
        systemGanttTask.setProjectName(scheduling.getProjectName());
        systemGanttTask.setProjectId(scheduling.getProjectId());
        systemGanttTask.setWorkload(scheduling.getWorkload());
        systemGanttTask.setSaturation(scheduling.getSaturation());
        systemGanttTask.setDeveloperId(scheduling.getDeveloperId());
        systemGanttTask.setDeveloperName(scheduling.getDeveloperName());

        return systemGanttTask;
    }

    public List<SystemGantt> getSystemListWithGanttTasks(){
        // 使用map便于查找相同的系统，为任务的放置提供位置
        Map<String, SystemGantt> systemGanttMap = new HashMap<>();
        List<SystemGantt> systemGanttList = new ArrayList<>();
        // 获得计划所有项
        List<Scheduling> schedulingList = algorithmSchedulingService.findAll();

        for(int i = 0; i < schedulingList.size(); i++){
            Scheduling scheduling = schedulingList.get(i);
            SystemGantt systemGantt = systemGanttMap.get(scheduling.getSystemId());
            if(systemGantt == null){
                SystemGantt gantt = new SystemGantt();
                List<SystemGanttTask> systemGanttTasks = new ArrayList<>();
                SystemGanttTask systemGanttTask = scheduling2SystemGanttTask(scheduling);

                gantt.setSystemId(scheduling.getSystemId());
                gantt.setSystemName(scheduling.getSystemName());
                systemGanttTasks.add(systemGanttTask);
                gantt.setGanttTasks(systemGanttTasks);
                systemGanttMap.put(scheduling.getSystemId(), gantt);
            } else {
                SystemGanttTask systemGanttTask = scheduling2SystemGanttTask(scheduling);
                List<SystemGanttTask> systemGanttTasks = systemGantt.getGanttTasks();
                systemGanttTasks.add(systemGanttTask);
                systemGantt.setGanttTasks(systemGanttTasks);
            }
        }
        // 最后将map中的数据放到list里
        systemGanttMap.forEach((key,value)->{
            systemGanttList.add(value);
        });

        return systemGanttList;
    }

    public List<ProjectGantt> getProjectListWithGanttTasks(){
        List<ProjectGanttTask> projectGanttTasks = ganttService.findAllProjectGanttTask();
        List<ProjectGantt> projectGanttList = new ArrayList<>();
        //主键放置projectId
        Map<String, ProjectGantt> projectGanttMap = new HashMap<>();
        for(int i = 0; i < projectGanttTasks.size(); i++){
            ProjectGanttTask projectGanttTask = projectGanttTasks.get(i);
            // 为了显示正确，需要将结束时间加一
            LocalDate newEndDate = DateUtil.date2LocalDate(projectGanttTask.getEndDate()).plusDays(1);
            projectGanttTask.setEndDate(DateUtil.localDate2Date(newEndDate));
            ProjectGantt projectGantt = projectGanttMap.get(projectGanttTask.getProjectId());
            if(projectGantt == null){ // map中没有此项目就创建
                ProjectGantt gantt = new ProjectGantt();
                List<ProjectGanttTask> projectGanttTaskList = new ArrayList<>();
                List<String> developerNameList = algorithmSchedulingService.findDeveloperNamesBySystemId(projectGanttTask.getSystemId());
                String developerNames = developerNameList.get(0);
                for(int j = 1; j < developerNameList.size(); j++){
                    developerNames += developerNameList.get(j);
                }

                gantt.setProjectId(projectGanttTask.getProjectId());
                gantt.setProjectName(projectGanttTask.getProjectName());
                projectGanttTask.setDeveloperNames(developerNames);
                projectGanttTaskList.add(projectGanttTask);
                gantt.setGanttTasks(projectGanttTaskList);
                projectGanttMap.put(projectGanttTask.getProjectId(), gantt);
            } else {
                projectGanttMap.remove(projectGanttTask.getProjectId());
                List<ProjectGanttTask> projectGanttTaskList = projectGantt.getGanttTasks();
                List<String> developerNameList = algorithmSchedulingService.findDeveloperNamesBySystemId(projectGanttTask.getSystemId());
                String developerNames = developerNameList.get(0);
                for(int j = 1; j < developerNameList.size(); j++){
                    developerNames += developerNameList.get(j);
                }
                projectGanttTask.setDeveloperNames(developerNames);
                projectGanttTaskList.add(projectGanttTask);
                projectGantt.setGanttTasks(projectGanttTaskList);
                projectGanttMap.put(projectGanttTask.getProjectId(), projectGantt);
            }
        }
        // 最后将map中的数据放到list里
        projectGanttMap.forEach((key,value)->{
            projectGanttList.add(value);
        });

        return projectGanttList;
    }
}
