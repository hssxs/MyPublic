package com.yy.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yy.pojo.Permission;
import com.yy.pojo.Role;
import com.yy.pojo.SysUser;
import com.yy.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：YY
 * @date ：Created in 2019/6/20
 * @description ：
 * @version: 1.0
 */

public class SpringSecurity implements UserDetailsService {

    @Reference
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名从数据库用户数据
        SysUser sysUser = userService.findAllByUsername(username);
        List<GrantedAuthority> authoritylist = new ArrayList<>();
        for (Role role : sysUser.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getName());
                authoritylist.add(authority);
            }
        }
        //2.返回安全框架需要的user
        User user = new User(sysUser.getUsername(), sysUser.getPassword(), authoritylist);
        return user;
    }
}
