package cn.gst;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.gst.mapper")
public class ModelingServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelingServerApplication.class);
    }
}
