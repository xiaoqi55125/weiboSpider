package com.abcve.weiboSpider.dao;

import com.abcve.weiboSpider.service.WeiboService;

/**
 * Created by zhicheng on 14/9/23.
 */
public interface WeiboServiceMapper {
     public WeiboService getCompleteUser(String oneid);
    public int updateWeiboSeivice(WeiboService weiboService);
    public int updateWeiboSeiviceToNull(String oneId);
}
