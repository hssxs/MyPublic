package com.yy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yy.dao.OrderSettingDao;
import com.yy.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @author ：YY
 * @date ：Created in 2019/6/15
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingDao orderSettingDao;
    @Override
    public void addOrderSetting(List<OrderSetting> settingArrayList) {
        if (settingArrayList != null && settingArrayList.size() > 0) {
            for (OrderSetting orderSetting : settingArrayList) {
                Date orderDate = orderSetting.getOrderDate();
                OrderSetting orderSettingList = orderSettingDao.findByOrderDate(orderDate);
                if (orderSettingList.getNumber() > 0) {
                    orderSettingDao.edit(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<OrderSetting> findByMonth(String month) {
        String beginDay = month + "-01";
        String lastDay = month + "-31";
        List<OrderSetting> orderSettingList = orderSettingDao.findByMonth(beginDay, lastDay);
        return orderSettingList;
    }

    @Override
    public void updateNumberByDate(OrderSetting orderSetting) {
        OrderSetting os = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        if (os.getNumber() > 0) {
            //如果当前日期有与预约设置，修改
            orderSettingDao.edit(orderSetting);
        } else {
            //没有  增加
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public void updateByRes(OrderSetting orderSetting) {
        orderSettingDao.edit(orderSetting);
    }
}
