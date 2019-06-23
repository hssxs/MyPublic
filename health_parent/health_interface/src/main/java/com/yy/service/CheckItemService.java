package com.yy.service;
import com.github.pagehelper.Page;
import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.entity.Result;
import com.yy.pojo.CheckItem;

import java.util.List;

/**
 * @author ：YY
 * @date ：Created in 2019/6/9
 * @description ：
 * @version: 1.0
 */
public interface CheckItemService {

    void add(CheckItem checkItem);

    PageResult findByPage(QueryPageBean queryPageBean);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    void deleteById(Integer id);

    List<CheckItem> findAll();

}
