package cn.itcast.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.Sku;
import cn.itcast.core.dao.SkuDao;
import cn.itcast.core.service.SkuService;

@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService {

	@Autowired
	private SkuDao skuDao;

	@Override
	public List<Sku> fingSkulistByProductId(Long productId) {
		// TODO Auto-generated method stub
		/*
		 * Example example = new Example(Sku.class); Criteria criteria =
		 * example.createCriteria(); criteria.andEqualTo("productId",
		 * productId); List<Sku> list = skuDao.selectByExample(example);
		 */
		List<Sku> list = skuDao.selectByProductId(productId);
		return list;
	}

	@Override
	public int updateSku(Sku sku) {
		// TODO Auto-generated method stub
		int i = skuDao.updateByPrimaryKeySelective(sku);
		return i;
	}

}
