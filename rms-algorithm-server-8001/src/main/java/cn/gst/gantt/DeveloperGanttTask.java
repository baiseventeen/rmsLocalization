package cn.gst.gantt;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 柏琪
 * @date 2024-05-14 14:42
 */
@Data
public class DeveloperGanttTask {
    private String id;
    private String text;
    private String systemId;
    private String developerName;
    private String developerId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Double saturation;
    private Double workload;
}
