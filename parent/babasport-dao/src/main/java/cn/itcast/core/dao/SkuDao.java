package cn.itcast.core.dao;

import java.util.List;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;

import cn.itcast.core.bean.Sku;
import cn.itcast.core.bean.SuperPojo;

public interface SkuDao extends Mapper<Sku> {

	List<Sku> selectByExample(Example example);

	int updateByPrimaryKeySelective(Sku sku);

	List<Sku> selectByProductId(Long productId);

	List<Sku> selectByProductIdAndOrderByPriceAsc(Long productId);

	List<SuperPojo> findSuperPojoByProductId(Long id);

}
