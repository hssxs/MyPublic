//package com.yy.service;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.yy.Utils.DateUtils;
//import com.yy.constant.MessageConstant;
//import com.yy.constant.RedisMessageConstant;
//import com.yy.dao.MemberDao;
//import com.yy.dao.OrderDao;
//import com.yy.dao.OrderSettingDao;
//import com.yy.entity.Result;
//import com.yy.pojo.Member;
//import com.yy.pojo.Order;
//import com.yy.pojo.OrderSetting;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author ：YY
// * @date ：Created in 2019/6/18
// * @description ：
// * @version: 1.0
// */
//@Service(interfaceClass = OrderService.class)
//@Transactional
//public class OrderServiceImpl1 implements OrderService {
//
//    @Autowired
//    OrderDao orderDao;
//    @Autowired
//    OrderSettingDao orderSettingDao;
//    @Autowired
//    MemberDao memberDao;
//    @Override
//    public Result addOrder(Map<String,Object> map) {
//        String validateCode = String.valueOf(map.get("validateCode"));
//        String telephone = String.valueOf(map.get("telephone"));
//        String sex = String.valueOf(map.get("sex"));
//        String name = String.valueOf(map.get("name"));
//        String idCard = String.valueOf(map.get("idCard"));
//        //Date orderDate = (Date)map.get("orderDate");
//        //获取日期
//        Object orderDateObj = map.get("orderDate");
//        String orderDateStr = String.valueOf(orderDateObj);
//        Date orderDate = null;
//        try {
//            orderDate = DateUtils.parseString2Date(orderDateStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        int setmealId = Integer.parseInt(String.valueOf(map.get("setmealId")));
//        //验证该日期是否进行日期设置
//        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
//        if (orderSetting == null) {
//            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
//        }
//        //验证该日期已预约人数是否大于可预约人数
//        int number = orderSetting.getNumber();
//        int reservations = orderSetting.getReservations();
//        if (number <= reservations) {
//            return new Result(false, MessageConstant.ORDER_FULL);
//        }
//        //验证该用户是否是会员
//        Member member = memberDao.findByPhoneNumber(telephone);
//        if (member == null) {
//            Member mem = new Member();
//            mem.setRegTime(new Date());
//            mem.setIdCard(idCard);
//            mem.setPhoneNumber(telephone);
//            mem.setName(name);
//            mem.setSex(sex);
//            memberDao.register(mem);
//        } else {
//            //验证改会员是否已预约
//            Integer memberId = member.getId();
//            Order order = new Order();
//            order.setMemberId(memberId);
//            order.setSetmealId(setmealId);
//            order.setOrderDate(orderDate);
//            int count = orderDao.findByCondition(order);
//            if (count > 0) {
//                return new Result(false, MessageConstant.HAS_ORDERED);
//            }
//        }
//        //添加预约信息
//        Order order = new Order();
//        order.setMemberId(member.getId());
//        order.setOrderDate(orderDate);
//        order.setOrderStatus(Order.ORDERSTATUS_NO);
//        order.setOrderType(String.valueOf(map.get("orderType")));
//        order.setMemberId(setmealId);
//        orderDao.add(order);
//        //更新web层OrderSetting,已预约人数+1
//        orderSetting.setReservations(reservations + 1);
//        orderSettingDao.edit(orderSetting);
//        return new Result(true, MessageConstant.ORDER_SUCCESS,order);
//    }
//
//    @Override
//    public Map<String, Object> findById(Integer id) {
//        return orderDao.findById(id);
//    }
//}
