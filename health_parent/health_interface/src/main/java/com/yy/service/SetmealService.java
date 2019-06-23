package com.yy.service;


import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/12
 * @description ：
 * @version: 1.0
 */
public interface SetmealService {

    void add(Integer[] checkgroupIds, Setmeal setmeal);

    PageResult findByPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String, String>> findsetmealCount();
}
