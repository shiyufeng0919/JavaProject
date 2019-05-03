package com.syf.activemq.topic;

import org.apache.log4j.Logger;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/*
* 监听接口 add by syf 2019.5.3
* */
public class TopicMessageListen implements MessageListener {
    private static Logger logger = Logger.getLogger(TopicMessageListen.class);

    @Override
    public void onMessage(Message message) {
        logger.info("监听==================监听");
        try {
            logger.info(message);
            TextMessage tm = (TextMessage)(message);
            logger.info(tm.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
