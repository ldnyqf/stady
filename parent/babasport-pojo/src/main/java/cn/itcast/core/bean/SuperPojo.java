package cn.itcast.core.bean;

import java.io.Serializable;
import java.util.TreeMap;

public class SuperPojo extends TreeMap<String, Object> implements Serializable {

	/**
	 * 设置实体类属性
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 已经设置好的实体类本身,实现连点设置属性效果
	 */
	private static final long serialVersionUID = 1L;

	public SuperPojo setProperty(String name, Object value) {
		this.put(name, value);
		return this;
	}

}
