package cn.itcast.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Test1 {

	public static void main(String[] args) throws IOException, TemplateException {
		// 创建freemarker配置器
		Configuration configuration = new Configuration();
		// 设置模板目录
		configuration.setDirectoryForTemplateLoading(new File("ftl"));
		// 加载制定模板
		Template template = configuration.getTemplate("xxx.html");
		// 设置处理的数据
		HashMap map = new HashMap();
		map.put("world", "世界");
		map.get("world");
		// 设置输出文件的位置
		FileWriter fileWriter = new FileWriter(new File("hello.html"));
		// 进行处理
		template.process(map, fileWriter);
	}
}
