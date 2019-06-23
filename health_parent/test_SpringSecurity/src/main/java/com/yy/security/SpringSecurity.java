//package com.yy.security;
//
//
//import com.yy.pojo.Role;
//import com.yy.pojo.SysUser;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author ：YY
// * @date ：Created in 2019/6/20
// * @description ：
// * @version: 1.0
// */
//public class SpringSecurity implements UserDetailsService {
//    //模拟从数据库查询用户数据
//    private static List<SysUser> userList = new ArrayList<>();
//    static{
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        SysUser user1 = new SysUser();
//        Role role1 = new Role();
//        role1.setName("ROLE_ADMIN");
//        Role role3 = new Role();
//        role3.setName("add");
//        user1.setUsername("zhangsan");
//        user1.setPassword(bCryptPasswordEncoder.encode("123"));
//        user1.getRoles().add(role1);
//        user1.getRoles().add(role3);
//
//
//        SysUser user2 = new SysUser();
//        Role role2 = new Role();
//        role2.setName("ROLE_USER");
//        Role role4 = new Role();
//        role4.setName("delete");
//        user2.setUsername("lisi");
//        user2.setPassword(bCryptPasswordEncoder.encode("123"));
//        user2.getRoles().add(role2);
//        user2.getRoles().add(role4);
//
//
//        userList.add(user1);
//        userList.add(user2);
//    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        for (SysUser sysUser : userList) {
//            if (username!=null&&username.equals(sysUser.getUsername())) {
//                List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
//                for (Role role : sysUser.getRoles()) {
//                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
//                    grantedAuthorityList.add(simpleGrantedAuthority);
//                }
//
//                User user = new User(sysUser.getUsername(),sysUser.getPassword(),grantedAuthorityList);
//                return user;
//            }
//        }
//
//
//        return null;
//    }
//}
