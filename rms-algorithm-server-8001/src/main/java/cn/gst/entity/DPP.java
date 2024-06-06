package cn.gst.entity;

import lombok.Data;

/**
 * DeveloperPositionProductivity
 * @author 柏琪
 * @date 2024-05-07 15:11
 */
@Data
public class DPP {
    private String developerId;
    private String developmentTechId;
    private Double productivity;
}
