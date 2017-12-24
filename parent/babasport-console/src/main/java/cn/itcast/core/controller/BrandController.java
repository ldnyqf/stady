package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.core.bean.Brand;
import cn.itcast.core.service.BrandService;
import cn.itcast.core.tools.Encoding;
import cn.itcast.core.tools.PageHelper.Page;

@RequestMapping()
@Controller
public class BrandController {

	@Autowired
	private BrandService brandService;

	// bradn/list页面跳转
	@RequestMapping(value = "console/brand/list")
	public String tobrand(Model model, String name, Integer isDisplay, Integer pageNum, Integer pageSize) {

		// List<Brand> brands = brandService.findAll();
		System.out.println(Encoding.encodeGetRequest(name));
		System.out.println(isDisplay);
		Brand brand = new Brand();
		brand.setName(Encoding.encodeGetRequest(name));
		brand.setIsDisplay(isDisplay);
		Page<Brand> page = brandService.queryByNameAndIsDisplay(brand, pageNum, pageSize);
		// List<Brand> brands = brandService.findAll();
		model.addAttribute("brands", page.getResult());
		model.addAttribute("name", Encoding.encodeGetRequest(name));
		model.addAttribute("isDisplay", isDisplay);
		model.addAttribute("pageNum", page.getPageNum());
		model.addAttribute("pageSize", page.getPageSize());
		model.addAttribute("pages", page.getPages());
		return "brand/list";
	}

	@RequestMapping(value = "console/brand/ajaxList")
	@ResponseBody
	public List<Brand> ajaxList() {
		List<Brand> brands = brandService.findAll();
		System.out.println(brands.size());
		return brands;
	}

	// bradn/edit页面跳转
	@RequestMapping(value = "console/brand/edit")
	public String toedit(Model model, Integer id) {
		Brand brand = brandService.queryById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}

	// brand编辑保存页面跳转
	@RequestMapping(value = "console/brand/saveedit")
	public String saveedit(Model model, Brand brand) {
		System.out.println(brand.getName());

		brandService.brandEditSave(brand);
		return "redirect:/console/brand/list.do";
	}

	// brand删除页面跳转
	@RequestMapping(value = "console/brand/delete")
	public String delete(Model model, Integer id) {
		System.out.println(id);
		brandService.deleteById(id);
		return "redirect:/console/brand/list.do";
	}

	// brand删除页面跳转
	@RequestMapping(value = "console/brand/deletes")
	public String deletes(Model model, Long[] ids, String name, Integer isDisplay, Integer pageNum, Integer pageSize) {
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", isDisplay);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", pageSize);
		brandService.deletesByIds(ids);
		return "forward:/console/brand/list.do";
	}

	// brand通用页面跳转
	@RequestMapping(value = "console/brand/{pageName}")
	public String toBrand(@PathVariable("pageName") String pageName) {

		return "brand/" + pageName;
	}

	// brand添加页面跳转
	@RequestMapping(value = "console/brand/doAdd")
	public String doAdd(Brand brand) {
		brandService.save(brand);
		return "redirect:/console/brand/list.do";
	}
}
