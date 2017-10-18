package cn.duanshaojie.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "cn.duanshaojie.web")
public class Application {
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
}
