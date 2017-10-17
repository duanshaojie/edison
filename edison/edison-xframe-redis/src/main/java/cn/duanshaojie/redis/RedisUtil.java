package cn.duanshaojie.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	private final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	//Redis服务器IP
	private static String ADDR = "127.0.0.1";

	//Redis的端口号
	private static int PORT = 6379;

	//访问密码
	private static String AUTH = "admin";

	//可用连接实例的最大数目，默认值为8；
	//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;

	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 100;

	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			//            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
			jedisPool = new JedisPool(config, ADDR, 6379);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 获取redis键值-object
	 * 
	 * @param key
	 * @return
	 */
	public static Object getObject(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			byte[] bytes = jedis.get(key.getBytes());
			if(!StringUtils.isEmpty(bytes)) {
				return unserizlize(bytes);
			}
		} catch (Exception e) {
			logger.error("getObject获取redis键值异常:key=" + key + " cause:" + e.getMessage());
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * 设置redis键值-object
	 * @param key
	 * @param value
	 * @param expiretime
	 * @return
	 */
	public static String setObject(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.set(key.getBytes(), serialize(value));
		} catch (Exception e) {
			logger.error("setObject设置redis键值异常:key=" + key + " value=" + value + " cause:" + e.getMessage());
			return null;
		} finally {
			if(jedis != null)
			{
				jedis.close();
			}
		}
	}

	public String setObject(String key, Object value,int expiretime) {
		String result = "";
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.set(key.getBytes(), serialize(value));
			if(result.equals("OK")) {
				jedis.expire(key.getBytes(), expiretime);
			}
			return result;
		} catch (Exception e) {
			logger.error("setObject设置redis键值异常:key=" + key + " value=" + value + " cause:" + e.getMessage());
		} finally {
			if(jedis != null)
			{
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * 删除key
	 */
	public Long delkeyObject(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.del(key.getBytes());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(jedis != null)
			{
				jedis.close();
			}
		}
	}

	/**
	 * 是否存在key
	 */
	public Boolean existsObject(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key.getBytes());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(jedis != null)
			{
				jedis.close();
			}
		}
	}

	//序列化 
	public static byte [] serialize(Object obj){
		ObjectOutputStream obi=null;
		ByteArrayOutputStream bai=null;
		try {
			bai=new ByteArrayOutputStream();
			obi=new ObjectOutputStream(bai);
			obi.writeObject(obj);
			byte[] byt=bai.toByteArray();
			return byt;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//反序列化
	public static Object unserizlize(byte[] byt){
		ObjectInputStream oii=null;
		ByteArrayInputStream bis=null;
		bis=new ByteArrayInputStream(byt);
		try {
			oii=new ObjectInputStream(bis);
			Object obj=oii.readObject();
			return obj;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
}
