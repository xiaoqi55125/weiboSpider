package com.abcve.weiboSpider.Entity;

/**
 * Created by zhicheng on 14/9/19.
 */
public class SinaUser {
    private int userid;
    private String userName;
    private int follow;
    private int follower;
    private int weibocnt;
    private String realid;

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getWeibocnt() {
        return weibocnt;
    }

    public void setWeibocnt(int weibocnt) {
        this.weibocnt = weibocnt;
    }

    public String getRealid() {
        return realid;
    }

    public void setRealid(String realid) {
        this.realid = realid;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }



}
