package com.yy.dao;

import com.yy.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：YY
 * @date ：Created in 2019/6/15
 * @description ：
 * @version: 1.0
 */
public interface OrderSettingDao {

    void edit(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findByMonth(String beginDay, String lastDay);

    OrderSetting findByOrderDate(Date orderDate);
}
