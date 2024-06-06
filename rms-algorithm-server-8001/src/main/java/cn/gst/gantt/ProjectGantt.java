package cn.gst.gantt;

import lombok.Data;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-14 14:42
 */
@Data
public class ProjectGantt {
    private String projectId;
    private String projectName;
    private List<ProjectGanttTask> ganttTasks;
}
