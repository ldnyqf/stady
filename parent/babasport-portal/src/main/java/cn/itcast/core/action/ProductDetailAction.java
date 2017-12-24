package cn.itcast.core.action;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.bean.SuperPojo;
import cn.itcast.core.service.ProductService;

@Controller
public class ProductDetailAction {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "portal/product")
	public String toDetail(Model model, Long id) {
		// 根据id查询出商品信息和和库存信息 封装到superpojo类中
		SuperPojo superPojo = productService.findProductById(id);
		System.out.println(superPojo.get("product"));
		System.out.println(superPojo.get("skus"));
		HashMap<Long, String> colors = new HashMap<Long, String>();
		List<SuperPojo> suksList = (List<SuperPojo>) superPojo.get("skus");
		for (SuperPojo sp : suksList) {
			colors.put((Long) sp.get("colorId"), (String) sp.get("colorName"));
		}
		model.addAttribute("colors", colors);
		model.addAttribute("superPojo", superPojo);
		return "product";
	}
}
