package cn.itcast.core.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import cn.itcast.core.bean.SuperPojo;
import cn.itcast.core.tools.PageHelper.Page;

public interface SolrService {

	Page<SuperPojo> findProductFromSolr(String keyword, String sort, Long pageNum, Long brandId, Long pa, Long pb)
			throws SolrServerException;

	void addProductToSolr(String ids) throws SolrServerException, IOException;

}
