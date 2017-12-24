package cn.itcast.core.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.bean.Color;
import cn.itcast.core.bean.SuperPojo;
import cn.itcast.core.service.ProductService;
import cn.itcast.core.service.StaticPageService;

/**
 * 消息回调处理类
 * 
 * @author libin
 *
 */
public class MyMessageListener implements MessageListener {

	@Autowired
	private StaticPageService staticPageService;
	@Autowired
	private ProductService productService;

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			ActiveMQTextMessage amessage = (ActiveMQTextMessage) message;
			String ids = amessage.getText();
			System.out.println("消费方接收到的消息是：" + ids);
			String[] split = ids.split(",");
			for (String id : split) {
				// 根据id查询出商品信息和和库存信息 封装到superpojo类中
				long productId = Long.parseLong(id);
				SuperPojo superPojo = productService.findProductById(productId);
				System.out.println(superPojo.get("product"));
				System.out.println(superPojo.get("skus"));
				// HashMap<Long, String> colors = new HashMap<Long, String>();
				// 因为freemarker遍历map不支持key值是数值型，所以把颜色保存为color对象 保存在set集合里
				// Hastset可以去重复也可以重写hascode方法
				Set<Color> colors = new HashSet<Color>();
				List<SuperPojo> suksList = (List<SuperPojo>) superPojo.get("skus");
				for (SuperPojo sp : suksList) {
					// colors.put((Long) sp.get("colorId"), (String)
					// sp.get("colorName"));
					Color color = new Color();
					Object object = sp.get("colorId");
					color.setId((Long) sp.get("colorId"));
					color.setName((String) sp.get("colorName"));
					colors.add(color);
				}
				// 将颜色对象传递到页面
				// model.addAttribute("colors", colors);
				// model.addAttribute("superPojo", superPojo);
				superPojo.setProperty("colors", colors);
				HashMap map = new HashMap();
				map.put("superPojo", superPojo);
				// 开始静态化
				staticPageService.staticProductPage(map, id);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}