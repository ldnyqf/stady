package cn.itcast.core.dao;

import java.util.List;

import cn.itcast.core.bean.Brand;

public interface BrandDao {

	List<Brand> findAll();

	List<Brand> queryByExample(Brand brand);

	Brand queryById(Integer id);

	void brandEditSave(Brand brand);

	void deleteById(Integer id);

	void deletesByIds(Long[] ids);

	void insert(Brand brand);

}
