package cn.gst.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目管理系统实体类
 * @author 柏琪
 * @date 2024-04-12 15:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSystem {

    private String id;
    private String projectId;
    private String systemId;
    private String responsiblePersonId;
    private Double totalWorkload;
    private Double demandWorkload;
    private Double designWorkload;
    private Double developWorkload;
    private Double testWorkload;
    private Double onlineWorkload;
    private Double actualWorkday;
    private Double investedWorkday;
    private Double actualWorkload;
    private Double investedWorkload;
    private Integer state;
    private String version;
    private Double priorityScore;

    private String name;
    private Double difficultyRate;
    private String developmentTech;

    // 前端显示所需字段
    private String projectName;
    private String responsiblePersonName;
    private String developmentTechName;
    private String stateName;

}