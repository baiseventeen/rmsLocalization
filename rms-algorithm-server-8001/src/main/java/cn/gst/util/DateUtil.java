package cn.gst.util;

import cn.gst.entity.HolidayEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.time.*;
import java.util.Date;
import java.util.Map;

/**
 * @author 柏琪
 * @date 2024-05-06 17:56
 */
public class DateUtil {
    public static Map<String, HolidayEntity> holidayMap;
    static {
        String str1 = FileUtil.readJsonFile("2024.json");
        holidayMap = JSON.parseObject(str1, new TypeReference<Map<String, HolidayEntity>>() {
        });
    }

    public static LocalDate string2localdate(String datestr){
        String[] strs = datestr.split("-");
        String str3 = (strs[2].split("T"))[0];
        return LocalDate.of(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(str3));
    }

    /**
     * 获得开始日期和结束日期，其中去除非工作日
     * 开始日期算在其中
     * @Param startDays [1, 2) --> 1 2 --> 2对应当日，向下取整意思不变
     * @Param endDays   (1, 2] --> 1 1 --> 0对应当天，向上取整-1 意思不变
     */
    public static LocalDate[] getStartAndEndDate(LocalDate date, double startDays, double endDays){
        int startDaysInteger = (int) Math.floor(startDays);
        // 结束天数需要特殊处理
        // 日期应该向前一天
        int endDaysInteger = (int) Math.ceil(endDays) - 1;

        LocalDate autoIncrementDate = date.minusDays(1);
        LocalDate startDate = date;
        LocalDate endDate = date;
        while(startDaysInteger >= 0 || endDaysInteger >= 0){
            // 找到下一个工作日
            autoIncrementDate = autoIncrementDate.plusDays(1);
            while(!isWorkingDay(autoIncrementDate)){
                autoIncrementDate = autoIncrementDate.plusDays(1);
            }
            if(startDaysInteger == 0){
                startDate = autoIncrementDate;
            }
            startDaysInteger--;
            if(endDaysInteger == 0){
                endDate = autoIncrementDate;
            }
            endDaysInteger--;
        }
        LocalDate[] result = new LocalDate[]{startDate, endDate};
        return result;
    }

    public static boolean isInteger(double number){
        return Math.ceil(number) == number;
    }

    public static int calculateWorkingDays(LocalDate endDate) {
        int workingDays = 0;
        LocalTime nowTime = LocalTime.now();
        LocalDate currentDate = date2LocalDate(new Date());
        // 如果当前时间是早上八点前，则开始时间为当天
        // 如果当前时间是早上八点及以后，则开始时间为第二天
        if(nowTime.getHour() >= 8){
            currentDate = currentDate.plusDays(1);
        }


        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !isHoliday(currentDate)) {
                workingDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return workingDays;
    }


    public static boolean isWorkingDay(LocalDate date){
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if(dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !isHoliday(date)){
            return true;
        } else {
            return false;
        }
    }

    public static int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;
        LocalDate currentDate = startDate;

        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
            if (isWorkingDay(currentDate)) {
                workingDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return workingDays;
    }

    public static boolean isHoliday(LocalDate date){
        HolidayEntity holiday = holidayMap.get(date.toString());
        if(holiday != null && holiday.isOffDay() == true){
            return true;
        }
        return false;
    }

    public static LocalDate date2LocalDate(Date date) {
        if(null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date localDate2Date(LocalDate localDate) {
        if(null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }


}
