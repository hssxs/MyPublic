package com.yy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：YY
 * @date ：Created in 2019/6/20
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("hasAuthority('add')")
    @RequestMapping("/add")
    public String add() {
        return "add";
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/delete")
    public String delete() {
        return "delete";
    }
}
