package cn.zkht;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController 等价于@Controller和@Requestbody
 * @author libin
 *
 */
@RestController
public class HelloController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello-2019";
	}

	@RequestMapping("/demo")
	public Demo demo() {
		Demo demo = new Demo();
		demo.setId(5);
		demo.setName("你好");

		return demo;

	}
}
