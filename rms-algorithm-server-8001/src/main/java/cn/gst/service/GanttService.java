package cn.gst.service;

import cn.gst.entity.Scheduling;
import cn.gst.entity.SchedulingPeriod;
import cn.gst.gantt.ProjectGanttTask;
import cn.gst.mapper.GanttMapper;
import cn.gst.util.DateUtil;
import cn.gst.util.RandomIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * @author 柏琪
 * @date 2024-05-15 17:04
 */
@Service
public class GanttService {

    @Autowired
    GanttMapper ganttMapper;
    @Autowired
    ProjectSystemService projectSystemService;
    @Autowired
    AlgorithmSchedulingService algorithmSchedulingService;

    public void addProjectGanttTask(ProjectGanttTask projectGanttTask){
        ganttMapper.addProjectGanttTask(projectGanttTask);
    }

    public void addProjectGanttTaskBySchedulingList(List<Scheduling> schedulingList){
        List<ProjectGanttTask> projectGanttTasks = new ArrayList<>();
        // 以systemId为主键，因为其具有唯一性
        Map<String, ProjectGanttTask> projectGanttTasksMap = new HashMap<>();
        for(int i = 0; i < schedulingList.size(); i++){
            Scheduling scheduling = schedulingList.get(i);
            ProjectGanttTask projectGanttTask = projectGanttTasksMap.get(scheduling.getSystemId());
            if(projectGanttTask == null){ //为系统新建一项任务
                ProjectGanttTask task = new ProjectGanttTask();

                task.setId(RandomIdUtil.createProjectGanttId());
                task.setSystemId(scheduling.getSystemId());
                task.setSystemName(scheduling.getSystemName());
                task.setProjectId(scheduling.getProjectId());
                task.setProjectName(scheduling.getProjectName());
                task.setStartDate(scheduling.getStartTime());
                task.setEndDate(scheduling.getEndTime());
                task.setWorkload(scheduling.getWorkload());
                task.setSaturation(scheduling.getSaturation());

                projectGanttTasksMap.put(scheduling.getSystemId(), task);
            } else { // 更新系统的值
                projectGanttTasksMap.remove(scheduling.getSystemId());
                System.out.println(scheduling.getSystemId());
                System.out.println(scheduling);
                // 修改2个值
                // 1.开始时间
                Date newStartDate = projectGanttTask.getStartDate().before(scheduling.getStartTime())?
                        projectGanttTask.getStartDate():
                        scheduling.getStartTime();
                projectGanttTask.setStartDate(newStartDate);
                System.out.println(projectGanttTask.getStartDate());
                System.out.println(scheduling.getStartTime());
                System.out.println(newStartDate);
                // 2.结束时间
                Date newEndDate = projectGanttTask.getEndDate().after(scheduling.getEndTime())?
                        projectGanttTask.getEndDate():
                        scheduling.getEndTime();
                projectGanttTask.setEndDate(newEndDate);
                System.out.println(projectGanttTask.getEndDate());
                System.out.println(scheduling.getEndTime());
                System.out.println(newEndDate);
                projectGanttTasksMap.put(scheduling.getSystemId(), projectGanttTask);
            }
        }

        // 通过Map.keySet使用iterator遍历key,然后通过key得到对应的value值
        Iterator<String> iterator = projectGanttTasksMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            ProjectGanttTask projectGanttTask = projectGanttTasksMap.get(key);
            projectGanttTask.setWorkload(projectSystemService.findBySystemId(key).getTotalWorkload());
            addProjectGanttTask(projectGanttTask);
        }

    }

    public List<ProjectGanttTask> findAllProjectGanttTask(){
        return ganttMapper.findAllProjectGanttTask();
    }
}
