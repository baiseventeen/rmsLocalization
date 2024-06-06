package cn.gst.gantt;

import lombok.Data;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-14 14:42
 */
@Data
public class SystemGantt {
    private String systemId;
    private String systemName;
    private List<SystemGanttTask> ganttTasks;
}
