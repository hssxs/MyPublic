package com.yy.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yy.Utils.POIUtils;
import com.yy.constant.MessageConstant;
import com.yy.entity.Result;
import com.yy.pojo.OrderSetting;
import com.yy.pojo.Setmeal;
import com.yy.service.OrderSettingService;
import com.yy.service.SetmealService;
import org.omg.CORBA.ObjectHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：YY
 * @date ：Created in 2019/6/15
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestBody MultipartFile excelFile) {
            System.out.println(excelFile);
        try {
            //解析excel，获取预约设置信息
            List<String[]> strList = POIUtils.readExcel(excelFile);
            System.out.println(strList.toString());
            List<OrderSetting> settingArrayList = new ArrayList<>();
            for (String[] str : strList) {
                OrderSetting orderSetting = new OrderSetting();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                orderSetting.setOrderDate(sdf.parse(str[0]));
                orderSetting.setNumber(Integer.parseInt(str[1]));
                //把预约设置信息写入数据库
                System.out.println("str[0]"+str[0]);
                System.out.println("str[1]"+str[1]);
                settingArrayList.add(orderSetting);
            }
            orderSettingService.addOrderSetting(settingArrayList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    @RequestMapping("/findByMonth")
    public Result findByMonth( String month) {
        try {
            List<OrderSetting> orderSettingList = orderSettingService.findByMonth(month);
            List<Map<String, Object>> leftobj = new ArrayList<>();
            for (OrderSetting orderSetting : orderSettingList) {
                int number = orderSetting.getNumber();
                int reservations = orderSetting.getReservations();
                Date orderDate = orderSetting.getOrderDate();
                int date = orderDate.getDate();

                System.out.println("orderDate"+orderDate);
                System.out.println("date"+date);
                HashMap<String, Object> map = new HashMap<>();
                map.put("number", number);
                map.put("reservations", reservations);
                map.put("date", date);
                leftobj.add(map);
            }
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, leftobj);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
    @RequestMapping("/updateNumberByDate")
    public Result updateNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.updateNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
