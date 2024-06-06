package cn.gst.query;

import lombok.Data;

/**
 * @author 柏琪
 * @date 2024-04-12 16:50
 */
@Data
public class ProjectSystemQuery extends BaseQuery{
    private String projectId;
    private String name;
    private Integer state;
    private String responsiblePersonName;
    private String developmentTech; // id
}
