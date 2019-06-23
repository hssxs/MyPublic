package com.yy.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yy.Utils.QiniuUtils;
import com.yy.constant.MessageConstant;
import com.yy.constant.RedisConstant;
import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.entity.Result;
import com.yy.pojo.Setmeal;
import com.yy.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author ：YY
 * @date ：Created in 2019/6/12
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    SetmealService setmealService;
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/add")
    public Result add(@RequestBody Map<String, Object> map) {
        //json数组
        JSONArray jsonArray = (JSONArray) map.get("checkgroupIds");
        Integer[] checkgroupIds = jsonArray.toArray(new Integer[]{});
        //json对象
        JSONObject jsonObject = (JSONObject) map.get("formData");
        Setmeal setmeal = jsonObject.toJavaObject(Setmeal.class);
        try {
            setmealService.add(checkgroupIds, setmeal);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/upload")
    public Result upload(@RequestBody MultipartFile imgFile) {
        try {
            //获取唯一文件名
            //真实文件名+uuid
            String uuidName = UUID.randomUUID().toString().replace("-", "");
            String originalFilename = imgFile.getOriginalFilename();
            //文件名后缀
            String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = uuidName+extendName;
            System.out.println(fileName);
            //七牛工具类 需要字符流，文件名
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //上传成功的图片存在redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean) {
       PageResult pageResult= setmealService.findByPage(queryPageBean);
        return pageResult;
    }
}


