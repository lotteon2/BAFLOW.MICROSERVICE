package kr.flowergarden.onedayclassservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OnedayclassServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnedayclassServiceApplication.class, args);
    }

}
