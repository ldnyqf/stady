package cn.itcast;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.core.bean.TestTb;
import cn.itcast.core.dao.TestTbDao;

@RunWith(SpringJUnit4ClassRunner.class) // 测试运行环境
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestTbTest {

	@Autowired
	private TestTbDao testTbDao;

	@Test
	public void testAdd() {
		TestTb testTb = new TestTb();
		testTb.setName("范冰冰");
		testTb.setBirthday(new Date());
		testTbDao.insertTestTb(testTb);
	}

	@Test
	public void test1() {
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.MIN_VALUE);
	}

	public static Boolean main(String[] args) {
		int x = 1;
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.MIN_VALUE);
		System.out.println(x == 1);
		return x == 1;
	}

	@Test
	public void test2() {
		String s1 = "abc";
		String s2 = "abc";
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
	}
}
