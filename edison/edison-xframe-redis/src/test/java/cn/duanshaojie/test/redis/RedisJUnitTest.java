package cn.duanshaojie.test.redis;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.duanshaojie.application.Application;
import cn.duanshaojie.service.IRedisService;
import cn.duanshaojie.service.impl.RedisLock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class RedisJUnitTest {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisJUnitTest.class);
	
	@Resource(name="redisService")
	private IRedisService redisService;
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	@Test
	public void set(){
		redisService.set("haha", "呵呵");
	}
	private CountDownLatch latch = new CountDownLatch(20);

	@Test
	public void get(){
		String ye = redisService.get("name");
		System.out.println(ye);
	}
	
	@Test
	public void test(){

		Thread thread1 = new Thread(){
			@Override
			public void run() {
				lock("thread1");
			}
		};
		Thread thread2 = new Thread(){
			@Override
			public void run() {
				lock("thread2");
			}
		};
		try {
			thread1.start();
			thread2.start();
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void lock(String name){
		RedisLock lock = new RedisLock(redisTemplate,"grab");
		try {
			if(lock.lock()){
				//需要加锁的代码
				for(int i =0;i<10;i++){
					latch.countDown();
					if("thread1".equals(name)){
						logger.info("{} 正在进行抢单 {}",name,i);
					}else{
						logger.info("{} 正在进行抢单 {} ------------",name,i);
					}
				}
			}
		} catch(Exception e){
			logger.error("错误",e);
		} finally{
			lock.unlock();
		}
	}
}
