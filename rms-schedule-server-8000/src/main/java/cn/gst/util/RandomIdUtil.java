package cn.gst.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 柏琪
 * @date 2024-04-15 13:28
 */
public class RandomIdUtil {

    public static String createId(){
        //获取时间戳
        Date date = new Date();
        Long timestamp = date.getTime();
        return "RAN-"+ timestamp;
    }

    public static String createSystemId(){

        return "SYS-"+ getVersionId();
    }

    public static String getVersionId(){
        Date date = new Date();
        //获取年月日
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String tempOne = simpleDateFormat.format(date);
        //获取时间戳
        Long temp2 = date.getTime();
        String tempTwo = temp2.toString();

        // 生成id
        String version = tempOne + "-" + tempTwo;
        return version;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
