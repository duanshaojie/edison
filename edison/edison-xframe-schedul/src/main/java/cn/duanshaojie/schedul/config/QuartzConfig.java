package cn.duanshaojie.schedul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "classpath:schedul.xml" })
public class QuartzConfig {

}
