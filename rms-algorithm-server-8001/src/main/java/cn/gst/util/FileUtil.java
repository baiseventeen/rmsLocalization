package cn.gst.util;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * @author 柏琪
 * @date 2024-05-06 19:13
 */
public class FileUtil {
    public static String readJsonFile(String fileName) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(fileName);
            InputStream inputStream = classPathResource.getInputStream();
            Reader reader = new InputStreamReader(inputStream);
//            int ch = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) != -1) {
//                sb.append((char) ch);
//            }
//            fileReader.close();
//            reader.close();
//            jsonStr = sb.toString();
//            return jsonStr;
            InputStream is = classPathResource.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String myData = "";
            String data = null;
            while ((data = br.readLine()) != null) {
//                System.out.println(data);
                myData += data + "\n";
            }
            br.close();
            isr.close();
            is.close();
            return myData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
