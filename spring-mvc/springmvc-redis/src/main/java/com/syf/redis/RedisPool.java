package com.syf.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPool implements RedisUtils {
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void set(String key, String value) {
        Jedis jedis = this.getJedis();
        jedis.set(key, value);
        this.releaseJedis(jedis);
    }

    @Override
    public void set(String key, String value, Integer seconds) {
        Jedis jedis = this.getJedis();
        jedis.set(key, value);
        jedis.expire(key, seconds);
        this.releaseJedis(jedis);
    }

    @Override
    public String get(String key) {
        Jedis jedis = this.getJedis();
        String result = jedis.get(key);
        this.releaseJedis(jedis);
        return result;
    }

    @Override
    public void del(String key) {
        Jedis jedis = this.getJedis();
        jedis.del(key);
        this.releaseJedis(jedis);
    }

    @Override
    public void expire(String key, Integer seconds) {
        Jedis jedis = this.getJedis();
        jedis.expire(key, seconds);
        this.releaseJedis(jedis);
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = this.getJedis();
        Long count = jedis.incr(key);
        this.releaseJedis(jedis);
        return count;
    }

    /**
     * 获取Jedis连接
     *
     * @return Jedis连接
     */
    public Jedis getJedis() {
        return this.jedisPool.getResource();
    }

    /**
     * 释放Jedis连接
     *
     * @param jedis
     *            jedis连接
     */
    public void releaseJedis(Jedis jedis) {
        jedis.close();
    }

}
