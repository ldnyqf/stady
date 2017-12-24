package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.bean.Brand;
import cn.itcast.core.tools.PageHelper.Page;

/**
 * 
 * 品牌业务接口
 * 
 * @author libin
 *
 */
public interface BrandService {

	List<Brand> findAll();

	Page<Brand> queryByNameAndIsDisplay(Brand brand, Integer pageNum, Integer pageSize);

	Brand queryById(Integer id);

	void brandEditSave(Brand brand);

	void deleteById(Integer id);

	void deletesByIds(Long[] ids);

	void save(Brand brand);

	List<Brand> getBrandsFromRedis();

}
