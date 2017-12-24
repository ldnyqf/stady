package cn.itcast.core.controller;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * springmvc处理器
 * 
 * @author libin
 *
 */
@RequestMapping()
@Controller
public class CenterController {

	// 通用页面跳转
	/**
	 * 跳转主页
	 * 
	 * @param pageName
	 * @return
	 */
	@RequestMapping(value = "console/{pageName}")
	public String toindex(@PathVariable("pageName") String pageName) {
		return pageName;
	}

	// 通用frame页面跳转
	@RequestMapping(value = "console/frame/{pageName}")
	public String toframe(@PathVariable("pageName") String pageName) {
		return "frame/" + pageName;
	}

	@Test
	public void test3() {
		String s1 = "abc";
		String s2 = "abc";
		System.out.println(s1.equals(s2));
		System.out.println(s1.hashCode() == s2.hashCode());
		String s3 = new String("abc");
		String s4 = new String("abc");
		System.out.println(s3.equals(s4));
		System.out.println(s3.hashCode() == s4.hashCode());

		int a = 5000;
		int b = 5000;
	}

}
