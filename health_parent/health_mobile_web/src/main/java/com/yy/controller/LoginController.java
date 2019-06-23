package com.yy.controller;

import com.yy.Utils.ValidateCodeUtils;
import com.yy.constant.MessageConstant;
import com.yy.constant.RedisMessageConstant;
import com.yy.entity.Result;
import com.yy.pojo.Member;
import com.yy.service.MemberService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/19
 * @description ：
 * @version: 1.0
 */
@RequestMapping("/login")
@RestController
public class LoginController {
    @Autowired
    JedisPool jedisPool;
    @Reference
    MemberService memberService;
    @RequestMapping("/check")
    public Result login(HttpServletResponse response, @RequestBody Map <String, Object>map) {
        String validateCode = String.valueOf(map.get("validateCode"));
        String telephone = String.valueOf(map.get("telephone"));
        String jedisCode = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + "-" + telephone);
        if (jedisCode == null & !jedisCode.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Member member = memberService.findByPhoneNumber(telephone);
        if (member == null) {
             member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.register(member);
        }
        Cookie cookie = new Cookie("login_member_phone", telephone);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
