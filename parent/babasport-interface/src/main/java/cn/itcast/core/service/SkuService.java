package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.bean.Sku;

public interface SkuService {

	List<Sku> fingSkulistByProductId(Long productId);

	int updateSku(Sku sku);

}
