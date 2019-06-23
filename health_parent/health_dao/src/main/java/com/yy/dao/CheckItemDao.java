package com.yy.dao;

import com.github.pagehelper.Page;
import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.pojo.CheckItem;

import java.util.List;

/**
 * @author ：YY
 * @date ：Created in 2019/6/9
 * @description ：
 * @version: 1.0
 */
public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> querypage(String queryString);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    Integer findFK(Integer id);

    void deleteById(Integer id);

    List<CheckItem> findAll();

    List<CheckItem> findCheckItemByCheckGroupId(Integer id);
}
