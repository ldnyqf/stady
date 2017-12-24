package cn.itcast.core.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import cn.itcast.core.bean.Product;
import cn.itcast.core.bean.SuperPojo;
import cn.itcast.core.tools.PageHelper.Page;

public interface ProductService {

	Page<Product> fingByExample(Product product, Integer pageNum, Integer pageSize);

	Integer saveProduct(Product product);

	void showProduct(Product product, String ids) throws SolrServerException, IOException;

	void doDelete(Long[] ids);

	void isHide(Long[] ids);

	SuperPojo findProductById(Long id);

}
