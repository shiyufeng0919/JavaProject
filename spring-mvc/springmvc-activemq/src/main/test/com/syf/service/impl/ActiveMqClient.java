package com.syf.service.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/*
* 压力测试客户端
*
* 这里用了HttpClient 来向服务器发送Post 请求，然后计数输出，可以多开几个线程，这里只开了一个线程
* */
public class ActiveMqClient {
    @Test
    public void test() {
        HttpClient httpClient = new HttpClient();
        new Thread(new Sender(httpClient)).start();

    }
}
class Sender implements Runnable {
    public static AtomicInteger count = new AtomicInteger(0);
    HttpClient httpClient;

    public Sender(HttpClient client) {
        httpClient = client;
    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"---Send message-"+count.getAndIncrement());
            PostMethod post = new PostMethod("http://127.0.0.1:8080/SendMessage");
            post.addParameter("msg", "Hello world!");
            httpClient.executeMethod(post);
            System.out.println(Thread.currentThread().getName()+"---Send message Success-"+count.getAndIncrement());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
