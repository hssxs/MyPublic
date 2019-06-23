package com.yy.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.yy.constant.MessageConstant;
import com.yy.entity.PageResult;
import com.yy.entity.QueryPageBean;
import com.yy.entity.Result;
import com.yy.pojo.CheckGroup;
import com.yy.pojo.CheckItem;
import com.yy.service.CheckGroupService;
import org.apache.zookeeper.Op;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ：YY
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    CheckGroupService checkGroupService;

    /**
     * 添加检查组
     * @param map
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody  Map<String,Object> map) {
        System.out.println(map);
        //从map集合中获取jsonArray
        JSONArray jsonArray = (JSONArray) map.get("checkitemIds");
        //吧jsonArray反序列化为整数类型数组
        Integer[] checkItemIds = jsonArray.toArray(new Integer[]{});
        System.out.println("jsonArray "+jsonArray+"checkItemIds "+ Arrays.toString(checkItemIds));
        //从集合中获取json对象
        JSONObject jsonObject = (JSONObject) map.get("checkGroup");
        //把json对象反序列化为检查组
        CheckGroup checkGroup = jsonObject.toJavaObject(CheckGroup.class);
        System.out.println("jsonObject "+jsonObject+"checkGroup "+checkGroup);
        try {
            checkGroupService.add(checkGroup,checkItemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 分页查询所有检查组
     * @param queryPageBean
     * @return
     */
        @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean) {
            try {
//                PageResult pageResult = checkGroupService.findByPage(queryPageBean);
//                System.out.println(pageResult);
//                return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
                PageResult pageResult = checkGroupService.findByPage(queryPageBean);
                System.out.println(queryPageBean+"queryPageBean");
                return pageResult;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    /**
     * 编辑 根据检查组id查询所有检查项
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

    /**
     * 编辑 根据检查组id查询本检查组数据包含的检查项
     * @param id
     * @return
     */
    @RequestMapping("/checkitemBycheckgroupId")
    public List<Integer> checkitemBycheckgroupId(Integer id) {
        List<Integer> idList= checkGroupService.checkitemBycheckgroupId(id);
        return idList;
    }
    @RequestMapping("/edit")
    public Result edit(Integer [] checkitemIds, @RequestBody CheckGroup checkGroup) {
        try {
            checkGroupService.edit(checkitemIds,checkGroup);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);

        }

    }

    @RequestMapping("/findAll")
    public List<CheckGroup> findAll() {
        try {
            List<CheckGroup> checkGroupList = checkGroupService.findAll();
            return checkGroupList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
