package cn.duanshaojie.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(value = "cn.duanshaojie")
@ImportResource(locations = "classpath:spring-beans.xml")
public class Application {
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
}
