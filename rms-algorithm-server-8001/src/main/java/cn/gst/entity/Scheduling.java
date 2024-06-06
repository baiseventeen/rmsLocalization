package cn.gst.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author 柏琪
 * @date 2024-05-14 09:54
 */
@Data
public class Scheduling {
    private String id;
    private String systemId;
    private String developerId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    private Double workload;
    private Double saturation;
    private String version;
    private String systemName;
    private String developerName;
    private String projectId;
    private String projectName;
}
