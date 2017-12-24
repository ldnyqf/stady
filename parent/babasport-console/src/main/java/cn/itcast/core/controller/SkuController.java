package cn.itcast.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSONObject;

import cn.itcast.core.bean.Sku;
import cn.itcast.core.service.SkuService;

@RequestMapping()
@Controller
public class SkuController {

	@Autowired
	private SkuService skuService;

	// SKU通用跳转页面
	@RequestMapping(value = "console/sku/{pageName}.do")
	public String tosku(@PathVariable("pageName") String pageName) {
		return "sku/" + pageName;
	}

	@RequestMapping(value = "console/sku/list.do")
	public String tosku(Model model, Long productId) {
		// 根据商品的id查询库存
		List<Sku> skus = skuService.fingSkulistByProductId(productId);
		model.addAttribute("skus", skus);
		return "sku/list";
	}

	@RequestMapping(value = "console/sku/updateSku.do")
	@ResponseBody
	public void updateSku(Model model, Sku sku, HttpServletResponse response) throws IOException {
		// 根据商品的id查询库存
		System.out.println(sku.toString());
		skuService.updateSku(sku);
		JSONObject jo = new JSONObject();
		jo.put("status", "更新库存成功");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jo.toString());
	}
}
