package org.edison.xframe.redis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JedisTest {
	private static final Logger logger = LoggerFactory.getLogger(JedisTest.class);
	
	@Test
	public void jedisTest(){
		logger.info("begin");
		String setResult = RedisUtil.setObject("name", "duanshaojie");
		logger.info("set result = {}",setResult);
		String getResult = (String) RedisUtil.getObject("name");
		logger.info("get result = {}",getResult);
	}
}
