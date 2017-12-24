package cn.itcast.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.TestTb;
import cn.itcast.core.dao.TestTbDao;
import cn.itcast.core.service.ITestTbService;

@Service("testTbService")
@Transactional
public class TestTbServiceImpl implements ITestTbService {

	@Autowired
	private TestTbDao testTbDao;

	public void saveTestTb(TestTb testTb1, TestTb testTb2) {
		// TODO Auto-generated method stub
		testTbDao.insertTestTb(testTb1);
		// throw new RuntimeException();
		// System.out.println(1 / 0);
		testTbDao.insertTestTb(testTb2);
	}

}
