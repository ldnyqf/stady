package cn.itcast.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.bean.Brand;
import cn.itcast.core.dao.BrandDao;
import cn.itcast.core.service.BrandService;
import cn.itcast.core.tools.PageHelper;
import cn.itcast.core.tools.PageHelper.Page;
import redis.clients.jedis.Jedis;

@Service("brandService")
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;
	@Autowired
	private Jedis jedis;

	@Override
	public List<Brand> findAll() {
		// TODO Auto-generated method stub
		Brand brand = new Brand();
		brand.setName(null);
		brand.setIsDisplay(null);
		return brandDao.queryByExample(brand);

	}

	@Override
	public Page<Brand> queryByNameAndIsDisplay(Brand brand, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		brandDao.queryByExample(brand);
		Page<Brand> endPage = PageHelper.endPage();
		return endPage;
	}

	@Override
	public Brand queryById(Integer id) {
		// TODO Auto-generated method stub
		return brandDao.queryById(id);
	}

	@Override
	public void brandEditSave(Brand brand) {
		// TODO Auto-generated method stub
		jedis.hset("brand", brand.getId() + "", brand.getName());
		brandDao.brandEditSave(brand);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		brandDao.deleteById(id);
		jedis.hdel("brand", id + "");
	}

	@Override
	public void deletesByIds(Long[] ids) {
		// TODO Auto-generated method stub
		brandDao.deletesByIds(ids);
	}

	@Override
	public void save(Brand brand) {
		// TODO Auto-generated method stub
		brand.setId(jedis.incr("foo"));
		brandDao.insert(brand);
	}

	@Override
	public List<Brand> getBrandsFromRedis() {
		// TODO Auto-generated method stub
		Map<String, String> all = jedis.hgetAll("brand");
		List<Brand> list = new ArrayList<Brand>();
		Set<Entry<String, String>> entrySet = all.entrySet();
		for (Entry<String, String> entry : entrySet) {
			Brand brand = new Brand();
			brand.setId(Long.parseLong(entry.getKey()));
			brand.setName(entry.getValue());
			list.add(brand);
		}
		return list;
	}

}
