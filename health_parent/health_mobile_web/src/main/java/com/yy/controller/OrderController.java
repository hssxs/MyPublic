package com.yy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yy.constant.MessageConstant;
import com.yy.constant.RedisMessageConstant;
import com.yy.entity.Result;
import com.yy.pojo.Order;
import com.yy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/18
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    JedisPool jedisPool;
    @Reference
    OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String, Object> map) {
        System.out.println(map.toString());
        String validateCode = String.valueOf(map.get("validateCode"));
        String telephone = String.valueOf(map.get("telephone"));
        String codeRedis = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER+"-"+telephone);
        System.out.println("codeRedis"+codeRedis);
        //用户输入验证码与redis不一致
        if (validateCode == null || !validateCode.equals(codeRedis)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            Result result = orderService.addOrder(map);
            return result;
        }
    }

    @RequestMapping("/findById")
    public Result findById( Integer id) {
        try {
            Map<String, Object> map = orderService.findById( id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
