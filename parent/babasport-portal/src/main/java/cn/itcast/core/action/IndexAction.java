package cn.itcast.core.action;

import java.util.List;
import java.util.TreeMap;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.bean.Brand;
import cn.itcast.core.bean.SuperPojo;
import cn.itcast.core.service.BrandService;
import cn.itcast.core.service.SolrService;
import cn.itcast.core.tools.Encoding;
import cn.itcast.core.tools.PageHelper.Page;

@Controller
public class IndexAction {

	@Autowired
	private SolrService solrService;
	// 注入brandservice 从brandservice里面获取品牌
	@Autowired
	private BrandService brandService;

	@RequestMapping(value = "/")
	public String toProtal() {
		// 根据id查询商品
		return "index";
	}

	/**
	 * 商品关键字搜索action
	 * 
	 * @param keyword
	 * @return
	 * @throws SolrServerException
	 */
	@RequestMapping(value = "search")
	public String doSearch(Model model, String keyword, String sort, Long pageNum, Long qbrandId, Long pa, Long pb)
			throws SolrServerException {
		// 根据传过来的关键字，去solr里面搜索商品信息
		String keywordname = Encoding.encodeGetRequest(keyword);
		Page<SuperPojo> page = solrService.findProductFromSolr(keywordname, sort, pageNum, qbrandId, pa, pb);
		List<SuperPojo> superPojos = page.getResult();
		System.out.println(superPojos.size());
		// 传过来的数据我们用超级类接受
		model.addAttribute("superPojos", superPojos);
		model.addAttribute("keyword", keywordname);
		model.addAttribute("sort2", sort);
		if (sort.equals("price asc")) {
			sort = "price desc";
		} else {
			sort = "price asc";
		}
		model.addAttribute("sort", sort);
		TreeMap<String, String> map = new TreeMap<>();
		if (pa != null || pb != null) {
			if (pb == -1) {
				model.addAttribute("qprice", pa + "以上");
				map.put("价格", pa + "以上");
			} else {
				model.addAttribute("qprice", pa + "-" + pb);
				map.put("价格", pa + "-" + pb);
			}
		}

		model.addAttribute("pageNum", page.getPageNum());
		// 获取品牌
		List<Brand> brands = brandService.getBrandsFromRedis();
		if (qbrandId != null) {
			for (Brand brand : brands) {
				if (qbrandId == brand.getId()) {
					map.put("品牌", brand.getName());
				}
			}
		}
		System.out.println(brands.size());
		model.addAttribute("brands", brands);
		model.addAttribute("brandId", qbrandId);
		model.addAttribute("pa", pa);
		model.addAttribute("pb", pb);
		model.addAttribute("map", map);
		return "search";
	}
}
