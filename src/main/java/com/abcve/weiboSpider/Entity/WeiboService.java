package com.abcve.weiboSpider.Entity;

/**
 * Created by zhicheng on 14/9/25.
 */
public class WeiboService {
    public WeiboService(){
    }
    private int oneId;
    private String completeUser;

    public int getOneId() {
        return oneId;
    }

    public void setOneId(int oneId) {
        this.oneId = oneId;
    }

    public String getCompleteUser() {
        return completeUser;
    }

    public void setCompleteUser(String completeUser) {
        this.completeUser = completeUser;
    }

    public WeiboService(int oneId, String completeUser) {
        this.oneId = oneId;
        this.completeUser = completeUser;
    }
}
