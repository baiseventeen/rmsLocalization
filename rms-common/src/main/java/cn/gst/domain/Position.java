package cn.gst.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private String id;
    private String positionName;
    private Integer positionLevelId;
    private Double salaryMax;
    private Double salaryMin;
    private String developmentTechId;
    private Double productivity;
}
