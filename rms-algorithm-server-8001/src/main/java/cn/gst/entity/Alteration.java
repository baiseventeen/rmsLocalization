package cn.gst.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 柏琪
 * @date 2024-05-20 16:19
 */
@Data
public class Alteration {
    private String id;
    private String requestDeveloperId;
    private String requestDeveloperName;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestDate;
    private String processDeveloperId;
    private String processDeveloperName;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date processDate;
    private String reprocessDeveloperId;
    private String reprocessDeveloperName;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reprocessDate;
    private Integer state;
    private Integer level;
    private Integer type;
    private String content;
}
