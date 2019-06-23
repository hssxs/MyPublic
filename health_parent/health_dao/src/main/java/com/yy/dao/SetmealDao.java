package com.yy.dao;

import com.github.pagehelper.Page;
import com.yy.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/12
 * @description ：
 * @version: 1.0
 */
public interface SetmealDao {

    void addSetmeal(Setmeal setmeal);

    void addSC(Integer checkgroupId, Integer id);

    Page<Setmeal> findString(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String, String>> findsetmealCount();

    List<Map<String, Object>> findHotSetmeal();

}

