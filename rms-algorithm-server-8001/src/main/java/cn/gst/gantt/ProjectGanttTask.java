package cn.gst.gantt;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-14 14:42
 */
@Data
public class ProjectGanttTask {
    private String id;
    private String systemName; // 系统名
    private String systemId;
    private String projectId;
    private String projectName;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Double workload;
    private Double saturation;
    private String developerNames;
}
