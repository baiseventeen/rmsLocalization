package cn.gst.service;

import cn.gst.entity.Scheduling;
import cn.gst.entity.SchedulingPeriod;
import cn.gst.mapper.AlgorithmSchedulingMapper;
import cn.gst.util.DateUtil;
import cn.gst.util.RandomIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 柏琪
 * @date 2024-05-14 10:00
 */
@Service
public class AlgorithmSchedulingService {

    @Autowired
    AlgorithmSchedulingMapper algorithmSchedulingMapper;
    @Autowired
    AlgorithmDeveloperService algorithmDeveloperService;
    @Autowired
    ProjectSystemService projectSystemService;

    public void add(Scheduling scheduling){
        algorithmSchedulingMapper.add(scheduling);
    }

    public List<Scheduling> addBySchedulingPeriodList(List<SchedulingPeriod> schedulingPeriodList, LocalDate startDate){
        List<Scheduling> schedulingList = new ArrayList<>();
        double days = 0;
        for(int i = 0; i < schedulingPeriodList.size(); i++){
            Scheduling scheduling = new Scheduling();
            LocalDate[] date = new LocalDate[2];

            SchedulingPeriod schedulingPeriod = schedulingPeriodList.get(i);
            date = DateUtil.getStartAndEndDate(startDate, days, days+schedulingPeriod.getNumOfDays());
            LocalDate startTime = date[0];
            LocalDate endTime = date[1];
            Map<String, String> projectInfo = projectSystemService.findProjectBySystemId(schedulingPeriod.getSystemId());
            String projectId = projectInfo.get("projectId");
            String projectName = projectInfo.get("projectName");

            scheduling.setStartTime(DateUtil.localDate2Date(startTime));
            scheduling.setEndTime(DateUtil.localDate2Date(endTime));
            scheduling.setId(RandomIdUtil.createSchedulingId());
            scheduling.setSystemId(schedulingPeriod.getSystemId());
            scheduling.setDeveloperId(schedulingPeriod.getDeveloperId());
            scheduling.setWorkload(schedulingPeriod.getWorkload());
            scheduling.setSaturation(schedulingPeriod.getSaturation());
            scheduling.setVersion("TODO");
            scheduling.setSystemName(projectSystemService.findBySystemId(schedulingPeriod.getSystemId()).getName());
            scheduling.setDeveloperName(algorithmDeveloperService.findById(schedulingPeriod.getDeveloperId()).getName());
            scheduling.setProjectId(projectId);
            scheduling.setProjectName(projectName);

            schedulingList.add(scheduling);
            add(scheduling);

            days += schedulingPeriod.getNumOfDays();
        }
        return schedulingList;
    }

    public List<Scheduling> findAll(){
        return algorithmSchedulingMapper.findAll();
    }
    public List<String> findDeveloperNamesBySystemId(String systemId){
        return algorithmSchedulingMapper.findDeveloperNamesBySystemId(systemId);
    }
}
