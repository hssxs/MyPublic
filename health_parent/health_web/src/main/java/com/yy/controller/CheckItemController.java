package com.yy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.yy.constant.MessageConstant;
import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.entity.Result;
import com.yy.pojo.CheckItem;
import com.yy.service.CheckItemService;
import net.sf.jsqlparser.statement.truncate.Truncate;
import org.apache.zookeeper.Op;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author ：YY
 * @date ：Created in 2019/6/9
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    CheckItemService checkItemService;
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        System.out.println(checkItem.toString());
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findByPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkItemService.findByPage(queryPageBean);
            return pageResult ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckItem checkItem=  checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        System.out.println(checkItem);
        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/deleteById")
    public Result delete(Integer id) {
        try {
            checkItemService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * 检查组中增加 中查询所有检查项
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckItem> checkItemList = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
        } catch (Exception e) {
            e.printStackTrace();
           return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
}
