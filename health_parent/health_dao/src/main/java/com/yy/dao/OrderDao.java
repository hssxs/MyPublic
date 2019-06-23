package com.yy.dao;

import com.yy.pojo.Member;
import com.yy.pojo.Order;
import com.yy.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/18
 * @description ：
 * @version: 1.0
 */
public interface OrderDao {

    void add(Order order);

    int findByCondition(Order order);

    Map<String, Object> findById(Integer id);

    long findCountByOrderDate(String reportDate);

    long findVisitsByOrderDate(String reportDate);

    long findCountByAfterOrderDate(String thisWeekMonday);

    long findVisitsCountByAfterOrderDate(String thisWeekMonday);
}
