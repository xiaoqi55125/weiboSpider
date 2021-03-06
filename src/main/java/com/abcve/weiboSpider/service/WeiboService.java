package com.abcve.weiboSpider.service;

import com.abcve.weiboSpider.Entity.SinaWeibo;
import com.abcve.weiboSpider.dao.SinaWeiboMapper;
import com.abcve.weiboSpider.dao.UserMapper;
import com.abcve.weiboSpider.dao.WeiboServiceMapper;
import com.abcve.weiboSpider.util.HttpUtility;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.CountDownLatch;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Created by zhicheng on 14/9/24.
 */
public class WeiboService implements Runnable  {
    private String userName;
    private CountDownLatch begin;
    private CountDownLatch end;

    public WeiboService() {
    }

    public WeiboService(String userName, CountDownLatch begin, CountDownLatch end) {
        this.userName = userName;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run(){
        try{
            begin.await();        //等待begin的状态为0
            if (userName.length()>0){
                try {
                    dealSinaWeiboData(1, userName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("===>微博用户->"+userName+" 抓取完成.");
            updateCompleteData(userName);
        }catch (InterruptedException e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            end.countDown();    //使end状态减1，最终减至0
        }
    }

    public  void dealSinaWeiboData (int page,String userName)throws IOException{
        String sinceId = findUserSinceIdByName(userName);
        String url = "http://api.weibo.com/2/statuses/user_timeline.json?source=211160679&screen_name="+
                URLEncoder.encode(userName, "utf-8")+
                "&count=100&page="+page+
                "&since_id="+sinceId;
        HttpUtility.sendGetRequest(url);
        String apiResult = HttpUtility.readSingleLineRespone();
        JsonParser jsonParser = new JsonParser();
        JsonObject wholeJson = (JsonObject) jsonParser.parse(apiResult);
        JsonArray statusJsonArray = wholeJson.getAsJsonArray("statuses");
        for (int j = 0; j<statusJsonArray.size(); j++){

            JsonObject oneWeibo = (JsonObject) statusJsonArray.get(j);
            String weiboId = oneWeibo.get("id").toString();
            String userID = oneWeibo.getAsJsonObject("user").get("id").toString();
            String weiboContent = oneWeibo.get("text").toString();
            int likes = Integer.parseInt(oneWeibo.get("attitudes_count").toString());
            int repost = Integer.parseInt(oneWeibo.get("reposts_count").toString());
            int comment = Integer.parseInt(oneWeibo.get("comments_count").toString());
            String date = oneWeibo.get("created_at").toString();
            SinaWeibo sinaWeibo = new SinaWeibo(weiboId,userID,weiboContent,likes,repost,comment,date);
            if(page==1 && j==0 ){
                //更新用户最新微博id since_id
                updateUserSinceId(weiboId,userID);
            }
            insertSinaWeibo(sinaWeibo);
        }
        if (statusJsonArray.size()>=99){
            dealSinaWeiboData(page+1, userName);
        }
    }

    /**
     * 根据微博对象插入微博
     * @param sinaWeibo
     */
    public  void insertSinaWeibo(SinaWeibo sinaWeibo) throws UnsupportedEncodingException {
        SqlSession sqlSession = getSessionFactory().openSession();
        SinaWeiboMapper sinaWeiboMapper = sqlSession.getMapper(SinaWeiboMapper.class);
        try {
            //判断是否存在
            if (sinaWeiboMapper.isExist(sinaWeibo.getWeiboid() )< 1){
                sinaWeiboMapper.addSinaWeiboFormEntity(sinaWeibo);
            }
        }catch (Exception ue){
            //有的手机用户发的微博包含手机自带表情,新浪微博使用4个字节存的,需处理
            String a = new String(sinaWeibo.getWeiboContent().getBytes("ISO-8859-1"), "utf-8");
            sinaWeibo.setWeiboContent(a);
            if (sinaWeiboMapper.isExist(sinaWeibo.getWeiboid())<1){
                sinaWeiboMapper.addSinaWeiboFormEntity(sinaWeibo);
            }
        }finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    /**
     * 更新已经完成抓取的用户
     * @param userName 用户名
     */
    public void updateCompleteData(String userName){
        SqlSession sqlSession = getSessionFactory().openSession();
        WeiboServiceMapper weiboServiceMapper = sqlSession.getMapper(WeiboServiceMapper.class);
        try {
            weiboServiceMapper.updateWeiboService(userName);
        }catch (Exception e){

        }finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    /**
     * 更新用户最新微博Id
     * @param sinceid
     * @param realid
     */
    public void updateUserSinceId(String sinceid, String realid){
        //public int updateSinaUserSinceId(String sinceid,String realid);
        SqlSession sqlSession = getSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        try {
            userMapper.updateSinaUserSinceId(sinceid, realid);
        }catch (Exception e){

        }finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    public String findUserSinceIdByName(String name){
        SqlSession sqlSession = getSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        String result = "";
        try {
            result=  userMapper.findUserSinceIdByName(name);
        }catch (Exception e){

        }finally {
            sqlSession.close();
        }
        return result;
    }


    /**
     * sql SqlSessionFactory
     * @return SqlSessionFactory
     */
    private static SqlSessionFactory getSessionFactory() {
        SqlSessionFactory sessionFactory = null;

        String resource = "configuration.xml";
        try {
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources
                    .getResourceAsReader(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
