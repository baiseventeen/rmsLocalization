package cn.gst.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

public class MailUtil {
    private static Session session;
    private static MailUtil mailUtil;

    public static MailUtil getMailUtil() {
        //第一次创建mailUtil时进行配置
        if (mailUtil == null) {
            Properties props = new Properties();
            // 发送邮件的服务器的IP和端口
            //设置发件人的SMTP的服务器地址
            props.put("mail.smtp.host", "smtp.qq.com");
            props.put("mail.smtp.port", 465);
            props.put("mail.smtp.ssl.enable", "true");
            //设置传输协议
            props.setProperty("mail.transport.protocol", "smtp");
            //设置用户的认证方式auth
            props.setProperty("mail.smtp.auth", "true");
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
            } catch (GeneralSecurityException e1) {
                e1.printStackTrace();
            }
            props.put("mail.smtp.ssl.socketFactory", sf);
            props.put("mail.smtp.socketFactory.port", 465);
            session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    //发件人邮件用户名、密码,xxxx自己设置
//                    return new PasswordAuthentication("1293205470@qq.com", "aylqeieklasljjhb");
                    return new PasswordAuthentication("2877912955@qq.com", "hhdhawyjaplodddi");
                }
            });
            session.setDebug(true);
            mailUtil = new MailUtil();
        }
        return mailUtil;
    }

    //发送邮件
    public void sendEmail(String email, String sender, String topic, String content) {
        try {
            // 设置session,和邮件服务器进行通讯。
            MimeMessage message = new MimeMessage(session);
            // 设置邮件主题
            message.setSubject(topic);
            // 发送 HTML 消息, 可以插入html标签
            message.setContent(content, "text/html;charset=UTF-8");
            // 设置邮件发送日期
            message.setSentDate(new Date());
            //设置发送邮件使用的邮箱地址
            Address address = new InternetAddress("2877912955@qq.com", sender, "UTF-8");
            message.setFrom(address);
            // 设置邮件接收方的地址
            Address toAddress = new InternetAddress(email);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            // 发送邮件
            Transport.send(message);
            System.out.println("send ok!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTopic1(String developerName){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>请查看最新的排期计划</title>\n" +
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
                "\tmaigin-top:10px;\n" +
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
                "      padding: 10px;\n" +
                "    }\n" +
                "\n" +
                "    .warm_tips .desc {\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .qr_warp {\n" +
                "      max-width: 140px;\n" +
                "      margin: 10px auto;\n" +
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
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <section class=\"email_warp\">\n" +
                "    <div id=\"reset-password-email\">\n" +
                "      <div class=\"logo\">\n" +
                "        <img src=\"https://img.pcsoft.com.cn/soft/202211/103216-637ed7b019487.jpg\" alt=\"logo\">\n" +
                "      </div>\n" +
                "\n" +
                "      <h1 class=\"email-title\">\n" +
                "        尊敬的" +
                developerName +
                "您好：\n" +
                "      </h1>\n" +
                "      <p>请您尽快查看最新的排期计划。</p>\n" +
                "\n" +
                "      <a class=\"links_btn\" onclick=\"window.open('http://localhost:5173/scheduleDeveloperView')\">查看排期计划</a>\n" +
                "\n" +
                "      <div class=\"warm_tips\">\n" +
                "        <div class=\"desc\">\n" +
                "          请您尽快完成操作。\n" +
                "        </div>\n" +
                "\n" +
                "        <p>如有任何疑问或无法完成注册，请通过如下方式与我们联系：</p>\n" +
                "        <p>邮箱：support@qq.cn</p>\n" +
                "        <p>微信：support</p>\n" +
                "\n" +
                "        <div class=\"qr_warp\">\n" +
                "          <img src=\"https://XXXXXXXXXXXX\" alt=\"微信二维码图片\">\n" +
                "        </div>\n" +
                "        <p>本邮件由系统自动发送，请勿回复。</p>\n" +
                "      </div>\n" +
                "\n" +
                "      <div class=\"email-footer\">\n" +
                "        <p>辽宁高速通有限责任公司</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </section>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n" +
                "\n";
    }
}
