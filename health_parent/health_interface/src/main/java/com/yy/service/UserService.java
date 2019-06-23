package com.yy.service;

import com.yy.pojo.SysUser;

/**
 * @author ：YY
 * @date ：Created in 2019/6/21
 * @description ：
 * @version: 1.0
 */
public interface UserService {

    SysUser findAllByUsername(String username);
}
