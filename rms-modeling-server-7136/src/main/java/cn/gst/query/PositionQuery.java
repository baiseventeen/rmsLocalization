package cn.gst.query;

import lombok.Data;

@Data
public class PositionQuery extends BaseQuery{
    private String name;
    private Integer id;
    private Integer levelId;
    private String developmentTechId;
}
