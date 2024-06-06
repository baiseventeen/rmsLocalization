package cn.gst.mapper;

import cn.gst.entity.Scheduling;
import cn.gst.gantt.ProjectGanttTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-13 12:39
 */
@Repository
public interface GanttMapper {

    void addProjectGanttTask(@Param("projectGanttTask") ProjectGanttTask projectGanttTask);

    List<ProjectGanttTask> findAllProjectGanttTask();
}
