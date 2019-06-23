package com.yy.service;

import com.github.pagehelper.Page;
import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.pojo.CheckGroup;
import com.yy.pojo.CheckItem;

import java.util.List;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
public interface CheckGroupService {


    void add(CheckGroup checkGroup,Integer[] checkItemId);

    PageResult findByPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> checkitemBycheckgroupId(Integer id);

    void edit(Integer [] checkitemIds, CheckGroup checkGroup);

    List<CheckGroup> findAll();
}
