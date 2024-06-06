package cn.gst.service;

import cn.gst.domain.ProjectSystem;
import cn.gst.entity.DPP;
import cn.gst.entity.DeveloperScheduling;
import cn.gst.entity.Scheduling;
import cn.gst.entity.SchedulingPeriod;
import cn.gst.param.ScheduleParams;
import cn.gst.util.DateUtil;
import cn.gst.util.RandomIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 柏琪
 * @date 2024-05-06 21:00
 */
@Service
public class AlgorithmService {

    @Autowired
    ProjectSystemService projectSystemService;
    @Autowired
    AlgorithmDeveloperService algorithmDeveloperService;
    @Autowired
    AlgorithmSchedulingPeriodService algorithmSchedulingPeriodService;
    @Autowired
    AlgorithmSchedulingService algorithmSchedulingService;
    @Autowired
    GanttService ganttService;

    public void algorithm(ScheduleParams scheduleParams){

        Double maxDailyWorkSaturation = scheduleParams.getMaxDailyWorkSaturation();
        Double maxAverageWorkSaturation = scheduleParams.getMaxAverageWorkSaturation();
        ArrayList<String> systemIdList = scheduleParams.getSystemIdList();
        ArrayList<String> developerIdList = scheduleParams.getDeveloperIdList();
        LocalDate startTime = scheduleParams.getStartTime();

        // 1.2 将所选系统按优先级评分进行排序并返回
        List<ProjectSystem> systemList = new ArrayList<>();
        for(String id: systemIdList){
            ProjectSystem projectSystem = projectSystemService.findBySystemId(id);
            if(projectSystem.getPriorityScore().equals("") || projectSystem.getPriorityScore() == null){
                projectSystem.setPriorityScore(80.0);
            }
            systemList.add(projectSystem);
        }
        systemList = systemList.parallelStream().sorted(
                Comparator.comparing(ProjectSystem::getPriorityScore).reversed()
        ).collect(Collectors.toList());

        // 2.处理开发人员列表
        // 2.1 为每一个开发人员创建一个分配时间列表
        List<DeveloperScheduling> developerSchedulingList = new ArrayList<>();
        for(int i = 0; i < developerIdList.size(); i++){
            DeveloperScheduling developerScheduling = new DeveloperScheduling();
            developerScheduling.setDeveloperId(developerIdList.get(i));
            developerScheduling.setSumOfDays(0.0);
            developerScheduling.setSchedulingPeriodList(new ArrayList<>());
            developerSchedulingList.add(developerScheduling);
        }
        // 2.2 每一个开发人员对应多个岗位和产能，存储所有开发人员对应的所有项
        List<DPP> dppList = algorithmDeveloperService.getDppByDeveloperIdList(developerIdList);

        // 3.算法
        for(ProjectSystem projectSystem: systemList){
            // 1.首先过滤符合系统开发技术的开发人员
            List<String> newDeveloperIdList = developerIdFilter(projectSystem, dppList);
            List<DeveloperScheduling> newDeveloperSchedulingList = developerSchedulingFilter(newDeveloperIdList, developerSchedulingList);
            List<DPP> newDppList = dppFilter(newDeveloperIdList, dppList, projectSystem.getDevelopmentTech());
            // 2.算法主体
            personnelUtilizationFirstAlgorithm(projectSystem, newDeveloperSchedulingList, newDppList, maxDailyWorkSaturation, maxAverageWorkSaturation, startTime);
        }

        // 4. 输出到数据库
        List<Scheduling> schedulingList = new ArrayList<>();
        for(int i = 0; i < developerIdList.size(); i++){
            // 4.1 获取开发人员时间分配列表
            List<SchedulingPeriod> schedulingPeriodList = developerSchedulingList.get(i).getSchedulingPeriodList();
            // 4.2 将时间分配列表的每一项都添加到scheduling_period数据库
            for(int j = 0; j < schedulingPeriodList.size(); j++){
                algorithmSchedulingPeriodService.add(schedulingPeriodList.get(j));
            }
            // TODO projectName和id插不进去
            // 4.3 添加到scheduling
            List<Scheduling> schedulings = algorithmSchedulingService.addBySchedulingPeriodList(schedulingPeriodList, startTime);
            schedulingList.addAll(schedulings);
        }
        // 4.4 将project_gantt表填写完整
        ganttService.addProjectGanttTaskBySchedulingList(schedulingList);
    }

    /**
     * 算法主题
     * @param projectSystem           需要进行排期的系统
     * @param developerSchedulingList 符合条件的开发人员的排期表
     * @param dppList                 符合条件的开发人员的岗位产能信息
     */
    public void personnelUtilizationFirstAlgorithm(ProjectSystem projectSystem,
                             List<DeveloperScheduling> developerSchedulingList,
                             List<DPP> dppList,
                             Double maxDailyWorkSaturation,
                             Double maxAverageWorkSaturation,
                                                   LocalDate startTime){
        int developerNum = developerSchedulingList.size();     // 记录符合条件的开发人员数量
        double workloadNum = projectSystem.getTotalWorkload();
        double[] workloadSum = new double[developerNum];
        double[] differencesOfTime = new double[developerNum]; // 用于记录开发人员总分配时间的差值,由低到高
        Map<String, Double> productivityMap = new HashMap<>();          // 将dpp转化为map，便于查找产能
        for(DPP dpp: dppList){
            productivityMap.put(dpp.getDeveloperId(), dpp.getProductivity());
        }
        double[] productivityRecord = new double[developerNum]; // 用于记录排序后的开发人员的产能

        // 对开发人员已分配的时间长度进行排序
        developerSchedulingList = developerSchedulingList.parallelStream().sorted(
                Comparator.comparing(DeveloperScheduling::getSumOfDays)
        ).collect(Collectors.toList());
        for(int i = 0; i < developerNum; i++){
            differencesOfTime[i] = developerSchedulingList.get(i).getSumOfDays();
            // 初始化productivityRecord数组
            String developerId = developerSchedulingList.get(i).getDeveloperId();
            productivityRecord[i] = productivityMap.get(developerId);
        }

        // 进行时间分配
        double[] finalDifferences = calculate(developerNum, workloadNum, workloadSum, differencesOfTime, productivityRecord, 1);
        double finalDifference = finalDifferences[0];
        double newFinalDifference = finalDifferences[1];

        // 判断是否超出了截止时间
        Date systemLastEndTime = projectSystemService.getLastEndTimeBySystemId(projectSystem.getSystemId());
        int workingDays = DateUtil.calculateWorkingDays(startTime, DateUtil.date2LocalDate(systemLastEndTime));
        if(workingDays >= newFinalDifference){ // 截止时间长于所需时间，可分配
            //记录以上结果
            recordInSchedulingPeriod(productivityRecord, developerNum, workloadSum, workloadNum, developerSchedulingList, finalDifference, newFinalDifference, projectSystem);
        } else { // 截止时间短于所需时间，不可可分配
            // 使用每日最大工作饱和度进行重新计算
            workloadNum = projectSystem.getTotalWorkload();
            for(int i = 0; i < developerNum; i++){
                differencesOfTime[i] = developerSchedulingList.get(i).getSumOfDays();
                workloadSum[i] = 0;
            }
            double[] finalDifferencesInMaxDaily = calculate(developerNum, workloadNum, workloadSum, differencesOfTime, productivityRecord, maxDailyWorkSaturation);
            finalDifference = finalDifferencesInMaxDaily[0];
            newFinalDifference = finalDifferencesInMaxDaily[1];
            if(workingDays < newFinalDifference){// 截止时间长于所需时间，可分配
                recordInSchedulingPeriod(productivityRecord, developerNum, workloadSum, workloadNum, developerSchedulingList, finalDifference, newFinalDifference, projectSystem);
            }else { // 截止时间仍然短于所需时间，不可分配
                // TODO
                // 压缩已分配的时段
                recordInSchedulingPeriod(productivityRecord, developerNum, workloadSum, workloadNum, developerSchedulingList, finalDifference, newFinalDifference, projectSystem);
            }
        }
    }

    public void recordInSchedulingPeriod(double[] productivityRecord,
                       int developerNum,
                       double[] workloadSum,
                       double workloadNum,
                       List<DeveloperScheduling> developerSchedulingList,
                       double finalDifference,
                       double newFinalDifference,
                       ProjectSystem projectSystem){
        // 处理工作量总数数组
        // 工作量计算公式： 剩余工作量 * （本人产能/总产能）
        // WARNING 这个工作量可能会有溢出问题
        double productivitySum = 0.0;
        for(int i = 0; i < productivityRecord.length; i++){
            productivitySum += productivityRecord[i];
        }
        for(int i = 0; i < developerNum; i++){
            workloadSum[i] = workloadSum[i] + workloadNum * (productivityRecord[i] / productivitySum);
        }
        // 记录最终天数
        // 将以上结果记录
        for(int i = 0; i < developerNum; i++){
            // CHANGE
            // 1.为开发人员时间分配列表赋值
            DeveloperScheduling developerScheduling = developerSchedulingList.get(i);
            double originalSumOfDays = developerScheduling.getSumOfDays();

            SchedulingPeriod schedulingPeriod = new SchedulingPeriod();
            schedulingPeriod.setDeveloperId(developerScheduling.getDeveloperId());
            schedulingPeriod.setSystemId(projectSystem.getSystemId());
            schedulingPeriod.setNumOfDays(newFinalDifference - originalSumOfDays);
            schedulingPeriod.setWorkload(workloadSum[i]);
            schedulingPeriod.setNo(developerScheduling.getSchedulingPeriodList().size());
            schedulingPeriod.setId(RandomIdUtil.createSchedulingPeriodId());

            if(finalDifference - originalSumOfDays >= 0.01){ // 大于时间精度，记录
                // 1.1 总天数
                developerScheduling.setSumOfDays(newFinalDifference);
                // 1.2 时间段信息
                schedulingPeriod.setSaturation((finalDifference - originalSumOfDays) / (newFinalDifference - originalSumOfDays));

            } else {// 小于时间精度，忽略天数总和
                schedulingPeriod.setSaturation(1.0);
            }
            // 2.将时间分配信息添加到该开发人员时间分配列表中
            developerScheduling.addSchedulingPeriodList(schedulingPeriod);
        }
    }

    public double[] calculate(int developerNum,
                              double workloadNum,
                              double[] workloadSum,
                              double[] differencesOfTime,
                              double[] productivityRecord,
                              double saturation){
        double[] midFinalDifferences = new double[2];
        // 遍历时间插值数组，判断时间是否一致
        for(int i = 0; i < developerNum - 1; i++){
            if(differencesOfTime[i] < differencesOfTime[i+1]){// 若时间不一致，则分配工作量将时间拉齐
                double productivitySum = 0.0;
                for(int j = 0; j < i + 1; j ++){// 为时间少的所有开发人员分配工作量, 开发人员数为 i
                    // 获得当下开发人员的产能并与此轮总产能加和
                    productivitySum += productivityRecord[j] * saturation;

                }
                // 此轮时间差
                double difference = differencesOfTime[i+1] - differencesOfTime[i];
                // 计算需要分配的工作量总数
                double assignWorkload = difference * productivitySum;
                // 判断剩余工作量是否大于需要分配的工作量
                if(workloadNum >= assignWorkload){ // 大于等于则可以分配
                    workloadNum = workloadNum - assignWorkload;
                    // 修改时间差数组 和 工作量总数数组
                    for(int k = 0; k < i+1; k++){
                        differencesOfTime[k] = differencesOfTime[i+1];
                        workloadSum[i] = workloadSum[i] + difference * productivityRecord[i];
                        // 处理此轮工作量正好为零时的情况，记录此时的天数
                        midFinalDifferences[0] = differencesOfTime[i+1];
                    }
                } else { //小于则重新计算，且等价于 **最后一次一次一致性运算**
                    developerNum = i;
                    break;
                }
            }
        }
        // 剩余工作量正好为0，则无需进行下一步一致性
        if(workloadNum == 0){
            midFinalDifferences[1] = midFinalDifferences[0];
            return midFinalDifferences;
        }
        // 最后一次一次一致性运算
        double productivitySum = 0.0;
        for(int i = 0; i< developerNum; i++){
            productivitySum += productivityRecord[i] * saturation;
        }
        // 通过剩余工作量除以总产能，获得天数
        // 测试的天数有可能是无限小数，因此需要进行处理
        double days = workloadNum / productivitySum;
        double finalDifference = differencesOfTime[developerNum - 1] + days;
        BigDecimal num = new BigDecimal(days);
        double newDays = num.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        double newFinalDifference = differencesOfTime[developerNum - 1] + newDays;
        double[] finalDifferences = new double[2];
        finalDifferences[0] = finalDifference;
        finalDifferences[1] = newFinalDifference;
        return finalDifferences;
    }

    /**
     * 三个过滤器方法
     */
    public List<String> developerIdFilter(ProjectSystem projectSystem, List<DPP> dppList){
        List<String> newDeveloperIdList = new ArrayList<>();
        for (DPP dpp: dppList){
            if (dpp.getDevelopmentTechId().equals(projectSystem.getDevelopmentTech())){
                newDeveloperIdList.add(dpp.getDeveloperId());
            }
        }
        return newDeveloperIdList;
    }

    public List<DPP> dppFilter(List<String> newDeveloperIdList, List<DPP> dppList, String developmentTechId){
        List<DPP> newDppList = new ArrayList<>();
        for(int i = 0; i < newDeveloperIdList.size(); i++){
            for(int j = 0; j < dppList.size(); j++){
                if(newDeveloperIdList.get(i).equals(dppList.get(j).getDeveloperId())
                        && dppList.get(j).getDevelopmentTechId().equals(developmentTechId)){
                    newDppList.add(dppList.get(j));
                    break;
                }
            }
        }
        return newDppList;
    }

    public List<DeveloperScheduling> developerSchedulingFilter(List<String> newDeveloperIdList, List<DeveloperScheduling> developerSchedulingList){
        List<DeveloperScheduling> newDeveloperSchedulingList = new ArrayList<>();
        for(String developerId: newDeveloperIdList){
            for(DeveloperScheduling developerScheduling: developerSchedulingList){
                if(developerId.equals(developerScheduling.getDeveloperId())){
                    newDeveloperSchedulingList.add(developerScheduling);
                    break;
                }
            }
        }
        return newDeveloperSchedulingList;
    }
}
