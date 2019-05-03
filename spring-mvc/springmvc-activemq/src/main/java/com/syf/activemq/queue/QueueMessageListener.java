package com.syf.activemq.queue;

import com.syf.activemq.topic.TopicMessageListen;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/*
* 消息监听者 add by syf 2019.5.3
*
* 实现接口的onMessage 方法，我们将需要的业务操作在里面解决，这样子，就完成了我们生产者-中间件-消费者，这样一个解耦的操作了。
* */
public class QueueMessageListener implements MessageListener {
    private static Logger logger = Logger.getLogger(TopicMessageListen.class);

    @Override
    public void onMessage(Message message) {
        logger.info("监听==================监听");
        try {
            logger.info("监听的消息:\t"+message);
            TextMessage tm = (TextMessage)(message);
            logger.info("QueueMessageListener监听到了文本消息：\t"+tm.getText());
            //do something
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
