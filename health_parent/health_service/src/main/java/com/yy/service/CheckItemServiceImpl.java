package com.yy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yy.dao.CheckItemDao;
import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：YY
 * @date ：Created in 2019/6/9
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService{
        @Autowired
        CheckItemDao checkItemDao;
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        System.out.println(queryPageBean);
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = checkItemDao.querypage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem=  checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public void deleteById(Integer id) {
        //1.判断此di是否被其他表关联，（外键）
        //有，提示不能删除
        //无，删除
        //不建议使用integer  如果查询到不存在此数据，返回null 报空指针异常
        //用integer好处  只要不为null  就有值
        Integer num = checkItemDao.findFK(id);
        if (num > 0) {
           throw  new RuntimeException("检查项被关联，无法删除!");
        } else {
            checkItemDao.deleteById(id);
        }
    }

    /**
     * 编辑时的 查询
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItemList= checkItemDao.findAll();
        return checkItemList;
    }
}
