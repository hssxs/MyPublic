package com.yy.service;



import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yy.constant.RedisConstant;
import com.yy.dao.SetmealDao;
import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/12
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;
    @Autowired
    JedisPool jedisPool;
    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        setmealDao.addSetmeal(setmeal);
        setSetmealAndCheckgroup(checkgroupIds, setmeal.getId());
        //保存图片到redis数据库
        savePic2Redis(setmeal.getImg());
    }

    private void savePic2Redis(String img) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
    }

    private void setSetmealAndCheckgroup(Integer[] checkgroupIds, Integer id) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSC(checkgroupId, id);
            }
        }
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
       Page<Setmeal> page= setmealDao.findString(queryPageBean.getQueryString());
        return new  PageResult(page.getTotal(),page);
    }

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> setmealList = setmealDao.findAll();
        return setmealList;
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Map<String, String>> findsetmealCount() {
        return setmealDao.findsetmealCount();
    }

}
