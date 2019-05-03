package com.syf.controller;

import com.syf.activemq.queue.ConsumerService;
import com.syf.activemq.queue.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

/*
* activemq消息队列  add by syf 2019.5.3
* */
@Controller("/activemq")
public class ActiveMQController {
    private Logger logger = LoggerFactory.getLogger(ActiveMQController.class);
    @Resource(name = "demoQueueDestination")
    private Destination destination;

    //注入队列消息生产者
    @Resource(name = "producerService")
    private ProducerService producer;

    //注入队列消息消费者
    @Resource(name = "consumerService")
    private ConsumerService consumer;

    @RequestMapping(value = "/SendMessage", method = RequestMethod.POST)
    @ResponseBody
    public void send(String msg) {
        logger.info(Thread.currentThread().getName()+"------------send to jms Start");
        producer.sendMessage(msg);
        logger.info(Thread.currentThread().getName()+"------------send to jms End");
    }

    @RequestMapping(value= "/ReceiveMessage",method = RequestMethod.GET)
    @ResponseBody
    public Object receive(){
        logger.info(Thread.currentThread().getName()+"------------receive from jms Start");
        TextMessage tm = consumer.receive(destination);
        logger.info(Thread.currentThread().getName()+"------------receive from jms End");
        return tm;
    }
}
