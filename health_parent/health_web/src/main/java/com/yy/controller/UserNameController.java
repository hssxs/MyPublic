package com.yy.controller;

import com.yy.constant.MessageConstant;
import com.yy.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：YY
 * @date ：Created in 2019/6/21
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/ViewName")
public class UserNameController {

    @RequestMapping("/findUsername")
    public Result ViewName(HttpServletRequest request) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

}
