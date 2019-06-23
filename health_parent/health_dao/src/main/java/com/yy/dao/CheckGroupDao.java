package com.yy.dao;

import com.github.pagehelper.Page;
import com.yy.entity.PageResult;
import com.yy.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
public interface CheckGroupDao {

    void add(CheckGroup checkGroup);

    /**
     * 1.@Param("checkItemId") 因为方法参数Integer checkItemId与实体类不一一对应，mapping中用#{checkItemId}
     * 2.Integer checkItemId 不变，mapping使用#{param1}
     */
    void addGI(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkItemId);

    Page<CheckGroup> findByString(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> checkitemBycheckgroupId(Integer id);

    void delete(Integer id);

    void edit(CheckGroup checkGroup);

    List<CheckGroup> findAll();

    CheckGroup findCheckGroupBySetMealId(Integer setmealId);

}
