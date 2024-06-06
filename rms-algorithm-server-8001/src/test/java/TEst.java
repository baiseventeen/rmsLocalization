import cn.gst.AlgorithmServerApplication;
import cn.gst.domain.Project;
import cn.gst.entity.Scheduling;
import cn.gst.mapper.AlgorithmDeveloperMapper;
import cn.gst.mapper.AlgorithmProjectSystemMapper;
import cn.gst.entity.HolidayEntity;
import cn.gst.mapper.AlgorithmSchedulingMapper;
import cn.gst.service.AlgorithmDeveloperService;
import cn.gst.service.AlgorithmSchedulingPeriodService;
import cn.gst.service.InvestmentService;
import cn.gst.service.ProjectSystemService;
import cn.gst.util.DateUtil;
import cn.gst.util.FileUtil;
import cn.gst.util.MailUtil;
import cn.gst.util.RandomIdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author 柏琪
 * @date 2024-05-06 18:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgorithmServerApplication.class)
public class TEst {
    @Test
    public void test1(){
        String str1 = FileUtil.readJsonFile("2024.json");
        Map<String, HolidayEntity> stringUserMap = JSON.parseObject(str1, new TypeReference<Map<String, HolidayEntity>>() {
        });

        System.out.println(stringUserMap);
    }

    @Test
    public void testLocalDate(){
        System.out.println(DateUtil.calculateWorkingDays(LocalDate.of(2024, 4, 30), LocalDate.of(2024, 5, 5)));
    }

    @Autowired
    AlgorithmProjectSystemMapper projectSystemMapper;
    @Test
    public void findById(){
        System.out.println(projectSystemMapper.findBySystemId("SYS-20240416-1713235474323"));
    }

    @Autowired
    AlgorithmDeveloperMapper algorithmDeveloperMapper;
    @Autowired
    AlgorithmDeveloperService algorithmDeveloperService;
    @Test
    public void tets19(){
        System.out.println(algorithmDeveloperService.getDppByDeveloperId("0a18bcf20da5467b95ee3d3475f07183"));
    }

    @Test
    public void testee(){
        double number = 1.2344;
        BigDecimal num = new BigDecimal(number);
        double newnumber = num.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();
        System.out.println(newnumber);
        System.out.println(number/newnumber);
        System.out.println(1.11/1.1);
    }

    @Test
    public void tteess(){
        System.out.println(projectSystemMapper.getLastEndTimeBySystemId("SYS-20240416-1713235474323"));
    }

    int[] array = new int[10];
    @Test
    public void arrat(){
        aaaa(array);
        System.out.println(array[1]);
    }

    public void aaaa(int[] array){
        array[1] = 1;
    }

    @Test
    public void iadjka(){
        double num = 1.1;
        System.out.println(Math.ceil(num));
    }

    @Test
    public void isS(){
        LocalDate date1 = LocalDate.of(2024, 5, 1);
        LocalDate date2 = LocalDate.of(2024, 5, 13);
        LocalDate[] myDate1 = DateUtil.getStartAndEndDate(date1, 0, 1);
        System.out.println(myDate1[0] +"-----" +myDate1[1]);
        LocalDate[] myDatew= DateUtil.getStartAndEndDate(date2, 1.1, 4.1);
        System.out.println(myDatew[0] +"-----" +myDatew[1]);
    }

    @Autowired
    AlgorithmSchedulingMapper algorithmSchedulingMapper;
    @Test
    public void isS1(){
        Scheduling scheduling = new Scheduling();
        scheduling.setId(RandomIdUtil.createSchedulingId());
        algorithmSchedulingMapper.add(scheduling);
    }

    @Autowired
    AlgorithmProjectSystemMapper algorithmProjectSystemMapper;
    @Autowired
    AlgorithmSchedulingPeriodService algorithmSchedulingPeriodService;
    @Autowired
    ProjectSystemService projectSystemService;
    @Test
    public void test11(){
//        Map map = algorithmProjectSystemMapper.findProjectBySystemId("SYS-20240513-1715577615674");
//        map.forEach((key, value)->{
//            System.out.println(key);
//            System.out.println(value);
//        });
        Map<String, String> map = projectSystemService.findProjectBySystemId("SYS-20240513-1715577615674");
        map.forEach((key, value)->{
            System.out.println(key);
            System.out.println(value);
        });
    }
    @Autowired
    InvestmentService investmentService;
    @Test
    public void adassada(){
        System.out.println(investmentService.fingBySearch("1"));
    }

    @Test
    public void testMail(){
        MailUtil mailUtil = MailUtil.getMailUtil();
        mailUtil.sendEmail("1270549063@qq.com", "你的爸爸", "微信转载三百块" ,"<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>重置您的 无敌软件的 密码</title>\n" +
                "\n" +
                "  <style>\n" +
                "    body,html,div,ul,li,button,p,img,h1,h2,h3,h4,h5,h6 {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    body,html {\n" +
                "      background: #fff;\n" +
                "      line-height: 1.8;\n" +
                "    }\n" +
                "\n" +
                "    h1,h2,h3,h4,h5,h6 {\n" +
                "      line-height: 1.8;\n" +
                "    }\n" +
                "\n" +
                "    .email_warp {\n" +
                "      height: 100vh;\n" +
                "      min-height: 500px;\n" +
                "      font-size: 14px;\n" +
                "      color: #212121;\n" +
                "      display: flex;\n" +
                "      /* align-items: center; */\n" +
                "      justify-content: center;\n" +
                "    }\n" +
                "\n" +
                "    .logo {\n" +
                "      margin: 3em auto;\n" +
                "      width: 200px;\n" +
                "      height: 60px;\n" +
                "    }\n" +
                "\n" +
                "    h1.email-title {\n" +
                "      font-size: 26px;\n" +
                "      font-weight: 500;\n" +
                "      margin-bottom: 15px;\n" +
                "      color: #252525;\n" +
                "    }\n" +
                "\n" +
                "    a.links_btn {\n" +
                "      border: 0;\n" +
                "      background: #4C84FF;\n" +
                "      color: #fff;\n" +
                "      width: 100%;\n" +
                "      height: 50px;\n" +
                "      line-height: 50px;\n" +
                "      font-size: 16px;\n" +
                "      margin: 40px auto;\n" +
                "      box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.15);\n" +
                "      border-radius: 4px;\n" +
                "      outline: none;\n" +
                "      cursor: pointer;\n" +
                "      transition: all 0.3s;\n" +
                "      text-align: center;\n" +
                "      display: block;\n" +
                "      text-decoration: none;\n" +
                "    }\n" +
                "\n" +
                "    .warm_tips {\n" +
                "      color: #757575;\n" +
                "      background: #f7f7f7;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .warm_tips .desc {\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .qr_warp {\n" +
                "      max-width: 140px;\n" +
                "      margin: 20px auto;\n" +
                "    }\n" +
                "\n" +
                "    .qr_warp img {\n" +
                "      max-width: 100%;\n" +
                "      max-height: 100%;\n" +
                "    }\n" +
                "\n" +
                "    .email-footer {\n" +
                "      margin-top: 2em;\n" +
                "    }\n" +
                "\n" +
                "    #reset-password-email {\n" +
                "      max-width: 500px;\n" +
                "    }\n" +
                "    #reset-password-email .accout_email {\n" +
                "      color: #4C84FF;\n" +
                "      display: block;\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <section class=\"email_warp\">\n" +
                "    <div id=\"reset-password-email\">\n" +
                "      <div class=\"logo\">\n" +
                "        <img src=\"https://lf3-cdn-tos.bytescm.com/obj/static/xitu_juejin_web/dcec27cc6ece0eb5bb217e62e6bec104.svg\" alt=\"logo\">\n" +
                "      </div>\n" +
                "\n" +
                "      <h1 class=\"email-title\">\n" +
                "        尊敬的<span>AAA</span>您好：\n" +
                "      </h1>\n" +
                "      <p>您正在为登录邮箱为如下地址的 啦啦啦 账户重置密码：</p>\n" +
                "      <b class=\"accout_email\">xxxx@abc.com</b>\n" +
                "\n" +
                "      <p>请注意，如果这不是您本人的操作，请忽略并关闭此邮件。</p>\n" +
                "      <p>如您确认重置 XXXX 的账户密码，请点击下方按钮。</p>\n" +
                "\n" +
                "      <a class=\"links_btn\" onclick=\"window.open('https:XXXXXXXXXXX')\">重置密码</a>\n" +
                "\n" +
                "      <div class=\"warm_tips\">\n" +
                "        <div class=\"desc\">\n" +
                "          为安全起见，以上按钮为一次性链接，且仅在24小时内有效，请您尽快完成操作。\n" +
                "        </div>\n" +
                "\n" +
                "        <p>如有任何疑问或无法完成注册，请通过如下方式与我们联系：</p>\n" +
                "        <p>邮箱：support@XXX.cn</p>\n" +
                "        <p>微信助理：XXXXX</p>\n" +
                "\n" +
                "        <div class=\"qr_warp\">\n" +
                "          <img src=\"https://XXXXXXXXXXXX\" alt=\"微信二维码图片\">\n" +
                "        </div>\n" +
                "        <p>本邮件由系统自动发送，请勿回复。</p>\n" +
                "      </div>\n" +
                "\n" +
                "      <div class=\"email-footer\">\n" +
                "        <p>您的智能项目助理</p>\n" +
                "        <p>XXXXXXXX</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </section>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n" +
                "\n");
    }

    @Test
    public void eqwe(){
//        System.out.println(algorithmDeveloperMapper.findAllEMail());
    }
}
