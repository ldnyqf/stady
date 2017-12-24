package cn.itcast.core.service;

import java.io.IOException;
import java.util.Map;

/**
 * 页面静态化接口
 * 
 * @author libin
 *
 */
public interface StaticPageService {

	/**
	 * 静态化产品页面详情
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void staticProductPage(Map<String, Object> map, String id) throws Exception, IOException;

}
