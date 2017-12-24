package cn.itcast;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.core.bean.TestTb;
import cn.itcast.core.service.ITestTbService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class ITestTbServiceTest {

	@Autowired
	private ITestTbService testTbService;

	@Test
	public void test() {
		TestTb testTb1 = new TestTb();
		testTb1.setName("成龙");
		testTb1.setBirthday(new Date());
		TestTb testTb2 = new TestTb();
		testTb2.setName("景甜");
		testTb2.setBirthday(new Date());
		testTbService.saveTestTb(testTb1, testTb2);
	}

}
