package com.yy.service;

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
public interface OrderSettingService {

    void addOrderSetting(List<OrderSetting> settingArrayList);

    List<OrderSetting> findByMonth(String date);

    void updateNumberByDate(OrderSetting orderSetting);


    void updateByRes(OrderSetting orderSetting);
}
