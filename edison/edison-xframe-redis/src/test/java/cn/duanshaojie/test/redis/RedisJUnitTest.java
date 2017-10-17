package cn.duanshaojie.test.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.duanshaojie.application.Application;
import cn.duanshaojie.service.IRedisService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class RedisJUnitTest {
	@Resource(name="redisService")
	private IRedisService redisService;
	
	@Test
	public void set(){
		redisService.set("haha", "呵呵");
	}
	
	
	@Test
	public void get(){
		String ye = redisService.get("name");
		System.out.println(ye);
	}
	
	
}
