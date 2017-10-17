package cn.duanshaojie.schedul.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("schedulJob")
public class SchedulJob {
	private static final Logger logger = LoggerFactory.getLogger(SchedulJob.class);
	
	@Scheduled(cron="0/1 * * * * ?")
	public void executeTask2(){
		Thread curren = Thread.currentThread();
		logger.info("注解型定时任务:"+curren.getId()+",name"+curren.getName());
	}
	
	public void executeTask(){
		Thread curren = Thread.currentThread();
		logger.info("配置型定时任务:"+curren.getId()+",name"+curren.getName());
	}
}
