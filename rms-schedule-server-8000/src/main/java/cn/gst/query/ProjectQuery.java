package cn.gst.query;

import lombok.Data;

import java.util.Date;

/**
 * 项目查询条件类
 * @author 柏琪
 */
@Data
public class ProjectQuery extends BaseQuery{

    private String name;
    private String version;
    private String responsiblePersonName;         // 项目负责人
    private String skillResponsiblePersonId;    // 技术负责人
    private String productResponsiblePersonId;  // 产品负责人
    private Integer state;
    private Integer priorityLevel;
    private String createPersonId;
    private Date startLastEndTime;
    private Date endLastEndTime;
}
