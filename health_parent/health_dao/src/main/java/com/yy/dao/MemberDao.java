package com.yy.dao;

import com.yy.pojo.Member;

/**
 * @author ：YY
 * @date ：Created in 2019/6/19
 * @description ：
 * @version: 1.0
 */
public interface MemberDao {

    void register(Member mem);

    Member findByPhoneNumber(String telephone);

    Integer findByMemberBeforeMonth(String month);

    long findNewMemberCount(String reportDate);

    long findTotalCount();

    long findMemberCountByAfterDate(String thisWeekMonday);
}
