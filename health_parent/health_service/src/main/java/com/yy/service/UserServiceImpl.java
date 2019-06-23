package com.yy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yy.dao.UserDao;
import com.yy.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：YY
 * @date ：Created in 2019/6/21
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Override
    public SysUser findAllByUsername(String username) {
        return userDao.findAllByUsername(username);
    }

}
