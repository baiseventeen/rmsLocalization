package cn.gst.service;

import cn.gst.domain.Developer;
import cn.gst.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-20 14:27
 */
@Service
public class MailService {

    @Autowired
    AlgorithmDeveloperService algorithmDeveloperService;
    public void sendEMail(){
        List<Developer> developerList = algorithmDeveloperService.findAll();
        MailUtil mailUtil = MailUtil.getMailUtil();
        for(Developer developer: developerList){
//            System.out.println("-------------------"+developer.getEmail());
            if(developer.getEmail() != null && !developer.getEmail().equals("")){
//                System.out.println("--------------------------------------"+developer.getEmail());
                mailUtil.sendEmail(developer.getEmail(), "高速通", "排期计划查看通知" ,mailUtil.getTopic1(developer.getName()));
            }
        }
    }

}
