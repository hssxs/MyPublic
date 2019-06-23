package com.yy.jobs;

import com.yy.Utils.QiniuUtils;
import com.yy.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;


/**
 * @author ：YY
 * @date ：Created in 2019/6/13
 * @description ：
 * @version: 1.0
 */
public class ClearImgJob {

    JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void clear() {
        //获取垃圾图片集合
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //删除
        for (String img : sdiff) {
            //删除七牛云中的图片
            QiniuUtils.deleteFileFromQiniu(img);
            //删除redis集合中的图片 srem
            Long srem = jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, img);
            System.out.println(srem);
        }
    }
}
