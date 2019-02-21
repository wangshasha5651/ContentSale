package com.contentsale.utils;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import com.contentsale.controller.CartController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * Created by wss on 2019/2/21.
 */

@Service
public class JedisAdapter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(0);

        pool = new JedisPool(poolConfig, "127.0.0.1", 6379, 3000, "12345");
    }

    public void closeJedis(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }


    /**
     *  以下是基本操作的封装
     */
    public String set(String key, String value){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.set(key, value);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return null;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public String setex(String key, int seconds, String value){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.setex(key, seconds, value);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return null;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }


    public String get(String key){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.get(key);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return null;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public long del(String key){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.del(key);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return 0;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public Boolean exists(String key){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.exists(key);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return false;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    /**
     *  以下是List集合操作的封装
     */
    public long lpush(String key, String value){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.lpush(key, value);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return 0;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public List<String> lrange(String key, int start, int end){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return null;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public long llen(String key){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.llen(key);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return 0;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public String lset(String key, int index, String value){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.lset(key, index, value);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return null;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public List<String> brpop(int timeout, String key){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return null;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }


    /**
     *  以下是Set集合操作的封装
     */
    public long sadd(String key, String value){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.sadd(key, value);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return 0;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public long srem(String key, String value){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.srem(key, value);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return 0;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public Boolean sismember(String key, String value){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.sismember(key, value);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return false;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    public long scard(String key){
        Jedis jedis = null;
        try{
            //获取资源
            jedis = pool.getResource();
            return jedis.scard(key);

        }catch (Exception e){
            logger.error("Jedis异常：" + e.getMessage());
            return 0;
        }finally {
            //关闭资源
            closeJedis(jedis);
        }
    }

    /**
     *  以下是list集合操作的封装
     */




    public static void print(int index, Object obj){
        System.out.println(String.format("%d, %s", index, obj.toString()));
    }

    public static void main(String[] argv){
        Jedis jedis = new Jedis();
        jedis.auth("12345");
//
//
//        jedis.lpush("mylist", "1");
//        jedis.lpush("mylist", "2");
//        jedis.lpush("mylist", "3");
//        print(0, jedis.lrange("mylist", 0, jedis.llen("mylist")-1));
//        jedis.close(); // 释放连接资源

    }




}
