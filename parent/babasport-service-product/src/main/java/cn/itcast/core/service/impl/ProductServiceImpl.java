package cn.itcast.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;

import cn.itcast.core.bean.Product;
import cn.itcast.core.bean.Sku;
import cn.itcast.core.bean.SuperPojo;
import cn.itcast.core.dao.ProductDao;
import cn.itcast.core.dao.SkuDao;
import cn.itcast.core.service.ProductService;
import cn.itcast.core.tools.PageHelper;
import cn.itcast.core.tools.PageHelper.Page;
import redis.clients.jedis.Jedis;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private Jedis jedis;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public Page<Product> fingByExample(Product product, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		example.setOrderByClause("createTime desc");
		if (product.getName() != null) {
			criteria.andLike("name", "%" + product.getName() + "%");
		}
		if (product.getBrandId() != null) {
			criteria.andEqualTo("brandId", product.getBrandId());
		}
		if (product.getIsShow() != null) {
			criteria.andEqualTo("isShow", product.getIsShow());
		}
		List<Product> products = productDao.selectByExample(example);
		Page<Product> endPage = PageHelper.endPage();
		return endPage;
	}

	@Override
	public Integer saveProduct(Product product) {
		// TODO Auto-generated method stub
		product.setIsShow(0);
		product.setCreateTime(new Date());
		Long incr = jedis.incr("foo");
		product.setId(incr);
		int insert = productDao.insert(product);
		// 上下架的同时更新库存
		String[] colors = product.getColors().split(",");
		String[] sizes = product.getSizes().split(",");
		for (String size : sizes) {
			for (String color : colors) {
				// 保存suk对象
				Sku sku = new Sku();
				sku.setProductId(product.getId());
				sku.setColorId(Long.parseLong(color));
				sku.setSize(size);
				sku.setCreateTime(new Date());
				sku.setStock(200);
				sku.setPrice(99.99f);
				skuDao.insert(sku);
			}
		}
		return insert;
	}

	@Override
	public void showProduct(Product product, final String ids) throws SolrServerException, IOException {
		// TODO Auto-generated method stub
		// 改造 商品上架的同时保存到solr服务器
		String[] split = ids.split(",");
		List list = new ArrayList<String>();
		for (String string : split) {
			list.add(string);
		}
		Example example = new Example(Product.class);
		example.createCriteria().andIn("id", list);
		// 修改商品属性
		productDao.updateByExampleSelective(product, example);
		if (product.getIsShow() != null && product.getIsShow() == 1) {

			// 作为生产方，当商品上架的时候，把商品ids发送给mq,将商品生成静态页面

			jmsTemplate.send("productIds", new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					// TODO Auto-generated method stub
					return session.createTextMessage(ids);
				}
			});

		}
	}

	@Override
	public void doDelete(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			productDao.deleteByPrimaryKey(id);
		}
	}

	@Override
	public void isHide(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			Product product = new Product();
			product.setId(id);
			product.setIsShow(0);
			System.out.println(id);
			productDao.updateByPrimaryKeySelective(product);
		}
	}

	@Override
	public SuperPojo findProductById(Long id) {
		// TODO Auto-generated method stub
		SuperPojo superPojo = new SuperPojo();
		// 根据id查询到产品
		Product product = productDao.selectByPrimaryKey(id);
		List<SuperPojo> superPojos = skuDao.findSuperPojoByProductId(id);
		superPojo.setProperty("product", product);
		superPojo.setProperty("skus", superPojos);
		return superPojo;
	}

}
