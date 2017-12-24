package cn.itcast.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;

import cn.itcast.core.bean.Product;
import cn.itcast.core.bean.Sku;
import cn.itcast.core.bean.SuperPojo;
import cn.itcast.core.dao.ProductDao;
import cn.itcast.core.dao.SkuDao;
import cn.itcast.core.service.SolrService;
import cn.itcast.core.tools.PageHelper;
import cn.itcast.core.tools.PageHelper.Page;

@Service("solrService")
public class SolrServiceImpl implements SolrService {

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;

	@Override
	public Page<SuperPojo> findProductFromSolr(String keyword, String sort, Long pageNum, Long qbrandId, Long pa,
			Long pb) throws SolrServerException {
		// TODO Auto-generated method stub
		// 先不考虑keyword为空的情况
		ArrayList<SuperPojo> list = new ArrayList<SuperPojo>();
		SolrQuery query = new SolrQuery("name_ik:" + keyword);
		if (sort != null && sort.trim().length() > 3) {
			SortClause sortClause = new SortClause(sort.split(" ")[0], sort.split(" ")[1]);
			query.addSort(sortClause);
		}
		if (qbrandId != null) {
			query.addFilterQuery("brandId:" + qbrandId);
		}
		// 价格
		if (pa != null && pb != null) {
			if (pb == -1) {
				query.addFilterQuery("price:[" + pa + " TO *]");
			} else {
				query.addFilterQuery("price:[" + pa + " TO " + pb + "]");
			}
		}
		// 设置高亮
		query.setHighlight(true);
		query.addHighlightField("name_ik");
		query.setHighlightSimplePre("<span style='color:red'>");
		query.setHighlightSimplePost("</span>");
		// 创建page类 里面包含查询起始索引和页面大小
		Page page = new Page(pageNum.intValue(), 30);
		query.setStart(page.getStartRow());
		query.setRows(page.getPageSize());
		QueryResponse response = solrServer.query(query);

		// 获取高亮数据集合
		// 第一个参数 数据id 第二个参数 string 高亮字段 list 字段内容的集合 因为可能有多值 所有用list接收
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

		SolrDocumentList results = response.getResults();
		// 满足条件的总条数 设置到page里的total里 得到pages总页数
		long numFound = results.getNumFound();
		page.setTotal(numFound);
		for (SolrDocument solrDoc : results) {
			SuperPojo pojo = new SuperPojo();
			String id = (String) solrDoc.getFieldValue("id");
			pojo.put("id", id);
			// String name = (String) solrDoc.getFieldValue("name_ik");
			// 把高亮字段设置到对象里
			Map<String, List<String>> map = highlighting.get(id);
			String name = map.get("name_ik").get(0);
			pojo.put("name", name);
			Long brandId = (Long) solrDoc.getFieldValue("brandId");
			pojo.put("brandId", brandId);
			String url = (String) solrDoc.getFieldValue("url");
			pojo.put("url", url);
			Float price = (Float) solrDoc.getFieldValue("price");
			pojo.put("price", price);
			list.add(pojo);
		}
		page.setResult(list);
		return page;
	}

	@Override
	public void addProductToSolr(String ids) throws SolrServerException, IOException {
		String[] split = ids.split(",");
		List list = new ArrayList<String>();
		for (String string : split) {
			list.add(string);
		}
		Example example = new Example(Product.class);
		example.createCriteria().andIn("id", list);
		List<Product> products = productDao.selectByExample(example);
		for (Product p : products) {
			// 把商品id商品名字 商品首图 品牌id放入solr
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", p.getId());
			doc.addField("name_ik", p.getName());
			doc.addField("url", p.getImgUrl().split(",")[0]);
			doc.addField("brandId", p.getBrandId());
			// 查询出商品的最低价格 放入solr
			Example exa = new Example(Sku.class);
			/*
			 * exa.createCriteria().andEqualTo("productId", p.getId());
			 * exa.setOrderByClause("price asc");
			 */
			PageHelper.startPage(1, 1);
			// List<Sku> skus = skuDao.selectByExample(exa);
			List<Sku> skus = skuDao.selectByProductIdAndOrderByPriceAsc(p.getId());
			PageHelper.endPage();
			doc.addField("price", skus.get(0).getPrice());
			solrServer.add(doc);
			solrServer.commit();
		}

	}

}
