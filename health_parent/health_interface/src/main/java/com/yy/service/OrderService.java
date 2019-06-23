package com.yy.service;

import com.yy.entity.Result;
import com.yy.pojo.Member;
import com.yy.pojo.Order;
import com.yy.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/18
 * @description ：
 * @version: 1.0
 */
public interface OrderService {

    Result addOrder(Map<String,Object> map);

    Map<String, Object> findById(Integer id);
}
