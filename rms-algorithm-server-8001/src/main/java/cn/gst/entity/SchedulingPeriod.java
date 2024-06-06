package cn.gst.entity;

import lombok.Data;

/**
 * @author 柏琪
 * @date 2024-05-07 15:04
 */
@Data
public class SchedulingPeriod {
    private String id;
    private String developerId;
    private String systemId;
    private Double numOfDays;
    private Double workload;
    private Double saturation;
    private Integer no;
}
