package cn.itcast.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;

import cn.itcast.core.bean.Color;
import cn.itcast.core.dao.ColorDao;
import cn.itcast.core.service.ColorService;

/**
 * colorService接口实现类
 * 
 * @author libin
 *
 */
@Service("colorService")
public class ColorServiceImpl implements ColorService {

	@Autowired
	private ColorDao colorDao;

	@Override
	public List<Color> findAll() {
		// TODO Auto-generated method stub
		Example example = new Example(Color.class);
		Criteria criteria = example.createCriteria();
		criteria.andNotEqualTo("parentId", 0);
		return colorDao.selectByExample(example);
	}

}
