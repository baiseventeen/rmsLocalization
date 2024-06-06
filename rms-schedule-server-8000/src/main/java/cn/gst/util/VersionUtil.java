package cn.gst.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VersionUtil {

    public static String create(){
        Date date = new Date();
        //获取年月日
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String tempOne = simpleDateFormat.format(date);
        //获取时间戳
        Long temp2 = date.getTime();
        String tempTwo = temp2.toString();

        // 生成id
        String projectVersion = tempOne + "-" + tempTwo;
        return projectVersion;
    }

}
