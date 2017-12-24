package cn.itcast.core.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginAction {

	// 显示登录页面
	@RequestMapping(value = "/login.aspx")
	public String showLogin() {
		return "login";
	}

	@RequestMapping(value = "/login.aspx", method = RequestMethod.POST)
	public String doLogin(String userName, String passwrod, String returnUrl) {
		if (returnUrl == null || returnUrl.length() < 1) {
			returnUrl = "http://localhost:8002/";
		}
		return "redirect" + returnUrl;
	}

}
