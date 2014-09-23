package com.abcve.weiboSpider.dao;

import com.abcve.weiboSpider.Entity.SinaWeibo;

import java.util.List;

/**
 * Created by zhicheng on 14/9/23.
 */
public interface SinaWeiboMapper {
    public SinaWeibo findWeiboById(String weiboid);
    public int findAllWeiboCnt();
    public int addSinaWeiboFormEntity(SinaWeibo sinaWeibo);
    public int isExist(String weiboid);
    public int updateSinaWeibo(SinaWeibo sinaWeibo);
    public int deleteOneSinaWeibo(String weiboid);
    public List<SinaWeibo> findWeiboByPageIndex(int pageIndex);
}
