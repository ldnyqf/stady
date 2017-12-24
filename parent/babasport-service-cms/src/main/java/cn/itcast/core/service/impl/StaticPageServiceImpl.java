package cn.itcast.core.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.itcast.core.service.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 静态化接口实现类
 * 
 * @author libin
 *
 */
@Service("staticPageService") // 继承servletContextAware来获取servletcongtext
public class StaticPageServiceImpl implements StaticPageService, ServletContextAware {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Override
	public void staticProductPage(Map<String, Object> map, String id) throws Exception, IOException {
		// 使用Spring的freemarker配置获取标准的freemarker配置器
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		// 生成文件的位置
		String realpath = servletContext.getRealPath("/html/product/" + id + ".html");
		System.out.println(realpath);
		// 获得最终文件的父目录
		File file = new File(realpath);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		// 加载模板文件
		Template template = configuration.getTemplate("product.html");
		// 设置文件的输出位置
		FileWriter writer = new FileWriter(new File(realpath));
		// 开始输出
		template.process(map, writer);

	}

	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}

}
