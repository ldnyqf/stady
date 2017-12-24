package cn.itcast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class) // 测试运行环境
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class JedisTest {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.57.103", 6379);
		Long incr = jedis.incr("foo");
		System.out.println(incr);
	}

	@Autowired
	private Jedis jedis;

	@Test
	public void testRedis() {
		Long incr = jedis.incr("foo");
		System.out.println(incr);
	}
}
