package com.yy.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yy.constant.MessageConstant;
import com.yy.entity.Result;
import com.yy.pojo.Setmeal;
import com.yy.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：YY
 * @date ：Created in 2019/6/16
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    SetmealService setmealService;
    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {
        try {
            List<Setmeal> setmealList = setmealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_SUCCESS);
        }
    }

    @RequestMapping("/findById")
    public Result setmeal(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
