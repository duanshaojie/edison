package cn.duanshaojie.schedul.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(value = "cn.duanshaojie.schedul")
@EnableScheduling
public class Application {
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
}
