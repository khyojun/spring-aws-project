package org.springboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing test시 jpaAuditing이 적용해야 하지만 webmvctest이기에 적용이 안됨! 그래서 위치를 변경
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
