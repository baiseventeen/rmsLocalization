package cn.gst.query;

import lombok.Data;

/**
 * 项目历史记录查询条件类
 * @author 柏琪
 */
@Data
public class ProjectHistroyQuery extends BaseQuery{

    private String projectId;
    private String version;

}
