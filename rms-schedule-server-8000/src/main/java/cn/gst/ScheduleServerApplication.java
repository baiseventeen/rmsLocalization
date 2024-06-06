package cn.gst;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.gst.mapper")
@EnableFeignClients(value="cn.gst.feignclient")
public class ScheduleServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleServerApplication.class);
    }
}
