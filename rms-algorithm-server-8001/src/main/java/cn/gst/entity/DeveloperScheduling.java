package cn.gst.entity;

import cn.gst.domain.Developer;
import lombok.Data;

import java.util.List;

/**
 * 开发人员时间分配表
 * @author 柏琪
 * @date 2024-05-07 15:09
 */
@Data
public class DeveloperScheduling {
    private String developerId;
    private Double sumOfDays;
    private List<SchedulingPeriod> schedulingPeriodList;

    public void addSchedulingPeriodList(SchedulingPeriod schedulingPeriod){
        this.schedulingPeriodList.add(schedulingPeriod);
    }
}
