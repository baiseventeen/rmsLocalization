package cn.gst.service;

import cn.gst.entity.SchedulingPeriod;
import cn.gst.mapper.AlgorithmSchedulingPeriodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-13 12:49
 */
@Service
public class AlgorithmSchedulingPeriodService {

    @Autowired
    AlgorithmSchedulingPeriodMapper algorithmSchedulingPeriodMapper;

    public void add(SchedulingPeriod schedulingPeriod){
        System.out.println("排错---------------------------------------------------"+schedulingPeriod);
        algorithmSchedulingPeriodMapper.add(schedulingPeriod);
    }


}
