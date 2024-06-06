package cn.gst.query;

import lombok.Data;

import java.util.Date;

@Data
public class DeveloperQuery extends BaseQuery{
    private String id;
    private String name;
    private Date startBirthDate;
    private Date endBirthDate;
    private Date startOnBoardDate;
    private Date endOnBoardDate;
}
