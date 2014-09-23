package com.abcve.weiboSpider.dao;

import com.abcve.weiboSpider.Entity.SinaUser;

import java.util.List;

/**
 * Created by zhicheng on 14/9/19.
 */
public interface UserMapper {
    public SinaUser findUserById(String userid);
    public List<SinaUser> findUserByPageIndex(int m);
    public List<SinaUser> findAllSinaUser();
    public int addSinaUser(String userName);
    public int addSinaUserFormEntity(SinaUser sinaUser);
    public int deleteOneUser(int userId);
    public int findAllUserCnt();
    public int isExist(String useName);
    public int updateSinaUser(SinaUser sinaUser);
}
