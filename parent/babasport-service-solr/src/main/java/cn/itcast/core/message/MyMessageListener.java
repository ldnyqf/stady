package cn.itcast.core.message;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.service.SolrService;

public class MyMessageListener implements MessageListener {

	@Autowired
	private SolrService solrService;

	@Override
	public void onMessage(Message message) {
		// 转换成接收方消息对象
		ActiveMQTextMessage amessage = (ActiveMQTextMessage) message;
		try {
			String ids = amessage.getText();
			// 执行保存到solr的方法
			solrService.addProductToSolr(ids);
		} catch (JMSException | SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
