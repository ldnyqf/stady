package cn.itcast.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

public class SolrTest {

	@Test
	public void test1() throws SolrServerException, IOException {
		// 使用httpserver服务断创建solr服务器对象
		Long a = 10L;
		Integer b = 10;

		System.out.println(a.equals(b));

	}

}
