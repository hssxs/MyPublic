package com.yy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yy.dao.MemberDao;
import com.yy.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：YY
 * @date ：Created in 2019/6/21
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;
    @Override
    public Member findByPhoneNumber(String telephone) {
        return memberDao.findByPhoneNumber(telephone);
    }

    @Override
    public void register(Member member) {
        memberDao.register(member);
    }

    @Override
    public List<Integer> findByMemberBeforeMonth(List<String> months) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String month : months) {
            month += "-31";
            Integer count = memberDao.findByMemberBeforeMonth(month);
            list.add(count);
        }
        return  list;
    }
}
