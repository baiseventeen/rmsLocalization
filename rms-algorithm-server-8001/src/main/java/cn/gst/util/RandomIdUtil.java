package cn.gst.util;

import java.util.UUID;

/**
 * @author 柏琪
 * @date 2024-05-13 12:56
 */
public class RandomIdUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String createSchedulingPeriodId(){
        return "SP-" + getUUID();
    }
    public static String createSchedulingId(){
        return "SCH-" + getUUID();
    }
    public static String createProjectGanttId(){
        return "PGT-" + getUUID();
    }
}
