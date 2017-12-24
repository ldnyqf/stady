package cn.itcast.core.converter;

import org.springframework.core.convert.converter.Converter;

public class MyConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		// 去掉字符串前后空格
		try {
			if (source != null) {
				source = source.trim();
				if (!"".equals(source)) {
					return source;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
