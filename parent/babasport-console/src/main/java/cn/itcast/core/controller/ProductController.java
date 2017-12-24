package cn.itcast.core.controller;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.bean.Color;
import cn.itcast.core.bean.Product;
import cn.itcast.core.service.BrandService;
import cn.itcast.core.service.ColorService;
import cn.itcast.core.service.ProductService;
import cn.itcast.core.tools.Encoding;
import cn.itcast.core.tools.PageHelper.Page;

/**
 * product相关业务action
 * 
 * @author libin
 *
 */
@RequestMapping()
@Controller
public class ProductController {

	@Autowired
	private BrandService brandService;
	@Autowired
	private ColorService colorService;
	@Autowired
	private ProductService productService;

	// 通用product页面跳转
	@RequestMapping(value = "console/product/{pageName}")
	public String toproduct(@PathVariable("pageName") String pageName) {
		return "product/" + pageName;
	}

	// 通用list页面跳转
	@RequestMapping(value = "console/product/list")
	public String toproductlist(Model model, String name, Long brandId, Integer isShow, Integer pageNum,
			Integer pageSize) {
		// 把条件封装到product对象里
		Product product = new Product();
		product.setName(Encoding.encodeGetRequest(name));
		System.out.println(Encoding.encodeGetRequest(name));
		product.setBrandId(brandId);
		System.out.println("商品品牌" + brandId);
		product.setIsShow(isShow);
		System.out.println("是否上下架" + isShow);
		Page<Product> page = productService.fingByExample(product, pageNum, pageSize);
		model.addAttribute("products", page.getResult());
		model.addAttribute("pageProduct", page);
		model.addAttribute("name", Encoding.encodeGetRequest(name));
		model.addAttribute("brandId", brandId);
		model.addAttribute("isShow", isShow);
		model.addAttribute("pages", page.getPages());
		model.addAttribute("pageNum", page.getPageNum());
		model.addAttribute("pageSize", page.getPageSize());
		return "product/list";
	}

	// product list页面action
	/**
	 * 跳转到add页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "console/product/add")
	public String toadd(Model model) {
		// 查询product 和color返回给页面
		/*
		 * List<Brand> brands = brandService.findAll();
		 * model.addAttribute("brands", brands);
		 */
		List<Color> colors = colorService.findAll();
		System.out.println("返回的颜色集合" + colors.size());
		model.addAttribute("colors", colors);
		return "product/add";
	}

	@RequestMapping(value = "console/product/doAdd")
	public String doAdd(Model model, Product product) {
		// 上传商品
		productService.saveProduct(product);
		return "redirect:/console/product/list.do";
	}

	/**
	 * 商品上架action
	 * 
	 * @param ids
	 * @return
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@RequestMapping(value = "console/product/isShow")
	public String isShow(String ids) throws SolrServerException, IOException {
		// 上架商品 从0改成1
		Product product = new Product();
		product.setIsShow(1);
		productService.showProduct(product, ids);
		return "redirect:/console/product/list.do";
		// return "forward:list.do";
	}

	@RequestMapping(value = "console/product/doDelete")
	public String doDelete(Long[] ids) {
		// 上架商品 从0改成1
		System.out.println("开始执行商品删除");
		System.out.println(ids.toString());
		productService.doDelete(ids);
		return "redirect:/console/product/list.do";
		// return "forward:list.do";
	}

	@RequestMapping(value = "console/product/isHide")
	public String isHide(Long[] ids) {
		// 上架商品 从0改成1
		System.out.println("开始执行商品下架");
		System.out.println(ids.toString());
		productService.isHide(ids);
		return "redirect:/console/product/list.do";
		// return "forward:list.do";
	}

}
