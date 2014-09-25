package com.abcve.weiboSpider.dao;

import com.abcve.weiboSpider.Entity.WeiboService;

/**
 * Created by zhicheng on 14/9/23.
 */
public interface WeiboServiceMapper {
    public WeiboService getCompleteUser(String oneid);
    public int updateWeiboService(String userName);
    public int updateWeiboServiceToNull(String oneId);
}
