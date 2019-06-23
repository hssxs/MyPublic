package com.yy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yy.dao.CheckGroupDao;
import com.yy.dao.CheckGroupDao;
import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.pojo.CheckGroup;
import com.yy.pojo.CheckItem;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
        @Autowired
        CheckGroupDao checkGroupDao;

    /**
     * 增加检查组
     * @param checkItemIds
     * @param checkGroup
     */
    @Override
    public void add( CheckGroup checkGroup,Integer[] checkItemIds) {
        //1.增加检查组表
        checkGroupDao.add(checkGroup);
        //2.增加检查组、检查项中间表
        setCheckGroupAndCheckItem(checkGroup.getId(),checkItemIds);
        System.out.println("id"+checkGroup.getId());
    }

    /**
     * 方法   增加增加检查组、检查项中间表
     * @param checkItemIds
     * @param checkGroupId
     */
    private void setCheckGroupAndCheckItem(Integer checkGroupId,Integer [] checkItemIds) {
        if (checkGroupId!=null&&checkItemIds!=null&&checkItemIds.length>0) {
            for (Integer checkItemId : checkItemIds) {
                checkGroupDao.addGI( checkGroupId,checkItemId);
            }
        }
    }

    /**
     * 分页查询所有检查组
      * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
       Page<CheckGroup> page= checkGroupDao.findByString(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page);
    }

    /**
     * 编辑 查询
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> checkitemBycheckgroupId(Integer id) {
        return checkGroupDao.checkitemBycheckgroupId(id);
    }

    @Override
    public void edit(Integer [] checkitemIds, CheckGroup checkGroup) {
        checkGroupDao.edit(checkGroup);
        checkGroupDao.delete(checkGroup.getId());
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
