package com.syf.service.impl;

import com.syf.activemq.queue.ConsumerService;
import com.syf.activemq.queue.ProducerService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

/*
* 测试activemq消息队列(点对点)-不可重复消费(一条消息只能够对应一个消费者)
*
*   消息生产者生产消息发送到queue中，然后消息消费者从queue中取出并且消费消息。
    消息被消费以后，queue中不再有存储，所以消息消费者不可能消费到已经被消费的消息。
    Queue支持存在多个消费者，但是对一个消息而言，只会有一个消费者可以消费、其它的则不能消费此消息了。
    当消费者不存在时，消息会一直保存，直到有消费消费
* */
@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class ActiveMqQueueTest {

    private static Logger logger = Logger.getLogger(ActiveMqQueueTest.class);

    @Resource(name = "demoQueueDestination")
    private Destination destination;

    //注入队列消息生产者
    @Resource(name = "producerService")
    private ProducerService producer;

    //注入队列消息消费者
    @Resource(name = "consumerService")
    private ConsumerService consumer;

    @Test
    public void TestProduceMsg(){
        String msg="hi activemq";
        logger.info(Thread.currentThread().getName()+"------------send to jms Start");
        producer.sendMessage(msg);
        logger.info(Thread.currentThread().getName()+"------------send to jms End");
    }

    @Test
    public void receive(){
        logger.info(Thread.currentThread().getName()+"------------receive from jms Start");
        TextMessage tm = consumer.receive(destination);
        logger.info(Thread.currentThread().getName()+"------------receive from jms End");
        logger.info(tm);
    }
}
