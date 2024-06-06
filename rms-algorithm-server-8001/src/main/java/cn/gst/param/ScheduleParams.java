package cn.gst.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-06 17:20
 */
@Data
public class ScheduleParams {
    private Double maxDailyWorkSaturation;
    private Double maxAverageWorkSaturation;
    private ArrayList<String> systemIdList;
    private ArrayList<String> developerIdList;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;
}
