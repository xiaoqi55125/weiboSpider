package com.abcve.weiboSpider.Entity;

import java.util.Date;

/**
 * Created by zhicheng on 14/9/23.
 */
public class SinaWeibo {
    public SinaWeibo(){

    }
    private String weiboid;
    private String userid;
    private String weiboContent;
    private int likes;
    private int repost;
    private int comment;
    private String createTime;

    public SinaWeibo(String weiboid, String userid, String weiboContent, int likes, int repost, int comment, String createTime) {
        this.weiboid = weiboid;
        this.userid = userid;
        this.weiboContent = weiboContent;
        this.likes = likes;
        this.repost = repost;
        this.comment = comment;
        this.createTime = createTime;
    }

    public String getWeiboid() {
        return weiboid;
    }

    public void setWeiboid(String weiboid) {
        this.weiboid = weiboid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWeiboContent() {
        return weiboContent;
    }

    public void setWeiboContent(String weiboContent) {
        this.weiboContent = weiboContent;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getRepost() {
        return repost;
    }

    public void setRepost(int repost) {
        this.repost = repost;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
