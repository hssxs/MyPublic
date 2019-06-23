package com.yy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yy.pojo.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：YY
 * @date ：Created in 2019/6/21
 * @description ：
 * @version: 1.0
 */

public interface MemberService {

    Member findByPhoneNumber(String telephone);

    void register(Member member);

    List<Integer> findByMemberBeforeMonth(List<String> months);
}
