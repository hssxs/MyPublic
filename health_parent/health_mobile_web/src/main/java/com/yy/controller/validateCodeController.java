package com.yy.controller;

import com.aliyuncs.exceptions.ClientException;
import com.yy.Utils.SMSUtils;
import com.yy.Utils.ValidateCodeUtils;
import com.yy.constant.MessageConstant;
import com.yy.constant.RedisConstant;
import com.yy.constant.RedisMessageConstant;
import com.yy.entity.Result;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.xml.ws.handler.MessageContext;

/**
 * @author ：YY
 * @date ：Created in 2019/6/17
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/validateCode")
public class validateCodeController {

    @Autowired
    JedisPool jedisPool;
    //发送验证码时将验证码保存到redis
    @RequestMapping("/send4Order")
    public Result validateCode(String telephone) {
        Integer ValidateCode = ValidateCodeUtils.generateValidateCode(4);
        //发送短信
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,ValidateCode.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //发送成功，将验证码保存到redis
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER+"-"+telephone,5*60 ,ValidateCode.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Login")
    public Result LoginValidateCode(String telephone) {
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_LOGIN, 5 * 60, validateCode.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
