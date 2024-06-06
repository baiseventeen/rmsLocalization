package cn.gst;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 柏琪
 * @date 2024-05-06 16:52
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.gst.mapper")
@EnableFeignClients(value="cn.gst.feignclient")
public class AlgorithmServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlgorithmServerApplication.class);
    }
}
