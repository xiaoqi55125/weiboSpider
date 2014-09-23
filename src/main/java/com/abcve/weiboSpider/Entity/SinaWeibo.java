package com.abcve.weiboSpider.Entity;

import java.util.Date;

/**
 * Created by zhicheng on 14/9/23.
 */
public class SinaWeibo {
    public SinaWeibo(){

    }
    private String weiboid;
    private int userid;
    private String weiboContent;
    private int likes;
    private int repost;
    private int comment;
    private Date createTime;

    public SinaWeibo(String weiboid, int userid, String weiboContent, int likes, int repost, int comment, Date createTime) {
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
