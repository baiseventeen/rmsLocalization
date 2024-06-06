package cn.gst.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 柏琪
 * @date 2024-05-18 23:23
 */
@Data
public class Investment {
    private String id;
    private String developerId;
    private String developerName;
    private String systemId;
    private String systemName;
    private Double workload;
    private Double workHour;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
