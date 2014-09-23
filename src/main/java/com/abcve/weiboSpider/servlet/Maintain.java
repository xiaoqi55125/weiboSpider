package com.abcve.weiboSpider.servlet;

import com.abcve.weiboSpider.Entity.SinaUser;
import com.abcve.weiboSpider.dao.SinaWeiboMapper;
import com.abcve.weiboSpider.dao.UserMapper;
import com.abcve.weiboSpider.util.HttpUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import  com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhicheng on 14/9/19.
 */
public class Maintain extends HttpServlet {

    private static final Log logger = LogFactory.getLog(Maintain.class);
    protected static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.index(req, resp);
        testMybatis() ;
        req.getRequestDispatcher("/WEB-INF/page/layout.jsp").forward(req, resp);
        //super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String pageIndex = req.getParameter("pageIndex");
        switch (action){
            case  "sinaUserList":
                this.getAllSinaUser(resp,Integer.parseInt(pageIndex));
                break;
            case "insertSinaUser":
                this.insertUserInfoFromScreenName(req,resp);
                break;
            case "delUser":
                this.delUser(req,resp);
                break;
            case "updateAllSinaUser":
                this.updateAllSinaUser(req,resp);
                break;

        }


    }

    /**
     * 初始化页面
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected  void index(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException{
        //has no session solution , cause by has no user login in action
        req.setAttribute("header", "/WEB-INF/page/firstLevelMenu.jsp");
        req.setAttribute("leftUserInfo", "/WEB-INF/page/leftUserInfo.jsp");
        req.setAttribute("mainContainer", "/WEB-INF/page/weibo/weibo.jsp");
        getAllSinaUser(resp,1);
    }

    /**
     * 测试方法,生产环境移除
     */
    protected void testMybatis() {
        SqlSession sqlSession = getSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        SinaWeiboMapper sinaWeiboMapper = sqlSession.getMapper(SinaWeiboMapper.class);
        //update sinceid
        userMapper.updateSinaUserSinceId("123","2656912373");


        sqlSession.commit();
    }

    /**
     * 处理页面加载完成,ajax获取所有用户
     * @param resp
     * @param pageIndex
     */
    protected void getAllSinaUser(HttpServletResponse resp,int pageIndex){
        SqlSession sqlSession = getSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.findAllUserCnt();
        try {
            List<SinaUser> sinaUsers = userMapper.findUserByPageIndex(pageIndex*10);
            Gson gsonnew ;
            GsonBuilder gb = new GsonBuilder();
            gb.serializeNulls();
            gsonnew = gb.create();
            String testData = gsonnew.toJson(sinaUsers);
            responseJsonData(resp,generateListSuccessJSONStr(testData,userMapper.findAllUserCnt(),pageIndex));
        } finally {
            sqlSession.close();
        }

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

    /**
     * 将返回的json对象输送到前端
     * @param resp
     * @param data
     */
    protected void responseJsonData(HttpServletResponse resp , String data){
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        try {
            resp.getWriter().print(data);
            logger.info(data);
        }catch(Exception e){
            String error = generateErrorJSONStr(e.getMessage());
            try {
                resp.getWriter().print(error);
            } catch (IOException ex) {

            }
        }
    }


    /**
     * 插入一个用户,同时插入其除了sinceid的值
     * @param req
     * @param resp
     * @throws IOException
     */
    protected void insertUserInfoFromScreenName(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //HttpRequestProxy
        req.setCharacterEncoding("UTF-8");
        String sinaUserScreenName = req.getParameter("sinaUserScreenName");
        SqlSession sqlSession = getSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        String url = "http://api.weibo.com/2/users/show.json?source=211160679&screen_name="+URLEncoder.encode(sinaUserScreenName, "utf-8");
        HttpUtility.sendGetRequest(url);
        String apiResult =HttpUtility.readSingleLineRespone();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(apiResult);
        logger.info(apiResult);
        String idstr = jsonObject.get("id").toString();
        int follow = Integer.parseInt(jsonObject.get("friends_count").toString());
        int follower = Integer.parseInt(jsonObject.get("followers_count").toString());
        int weibocnt = Integer.parseInt(jsonObject.get("statuses_count").toString());
        SinaUser sinaUser = new SinaUser(sinaUserScreenName,follow,follower,weibocnt,idstr);
        //isExist
        if(userMapper.isExist(sinaUserScreenName) > 0){
            try {
                userMapper.updateSinaUser(sinaUser);
                sqlSession.commit();
            } finally {
                sqlSession.close();
            }
        }else{
            //userMapper.findAllUserCnt();
            try {
                userMapper.addSinaUserFormEntity(sinaUser);
                sqlSession.commit();
            } finally {
                sqlSession.close();
            }
        }
    }

    /**
     * 从数据库中删除用户
     * @param req
     * @param resp
     */
    protected void delUser(HttpServletRequest req,HttpServletResponse resp){
        String userId = req.getParameter("userid");
        SqlSession sqlSession = getSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        try {
            userMapper.deleteOneUser(Integer.parseInt(userId));
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 更新用户信息
     * @param req
     * @param resp
     * @throws IOException
     */
    protected void updateAllSinaUser(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        SqlSession sqlSession = getSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        try{
            List<SinaUser> sinaUsers = userMapper.findAllSinaUser();
            for(SinaUser sinaUser:sinaUsers) {
                //userMapper.updateSinaUser(sinaUser);
                String url = "http://api.weibo.com/2/users/show.json?source=211160679&screen_name="+URLEncoder.encode(sinaUser.getUserName(), "utf-8");
                HttpUtility.sendGetRequest(url);
                String apiResult =HttpUtility.readSingleLineRespone();
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(apiResult);
                String idstr = jsonObject.get("id").toString();
                int follow = Integer.parseInt(jsonObject.get("friends_count").toString());
                int follower = Integer.parseInt(jsonObject.get("followers_count").toString());
                int weibocnt = Integer.parseInt(jsonObject.get("statuses_count").toString());
                SinaUser sinaUser2 = new SinaUser(sinaUser.getUserName(),follow,follower,weibocnt,idstr);
                userMapper.updateSinaUser(sinaUser2);
                sqlSession.commit();
            }

        }finally {
            sqlSession.close();
        }
    }

    //apis
    //get weibo info
    //http://api.weibo.com/2/statuses/timeline_batch.json?source=211160679&screen_names=黄健翔,一个汉字两个字节,海信手机官方微博
    //get user info
   // http://api.weibo.com/2/users/show.json?source=211160679&screen_name=一个汉字两个字节
    protected String generateErrorJSONStr(String errorMsgStr) {
        return "{\"Result\":\"ERROR\",\"Message\":\"" + errorMsgStr + "\"}";
    }
    protected String generateListSuccessJSONStr(String recordsJsonStr, int total,int  pageIndex) {
        return "{\"Result\":\"OK\",\"Records\":" + recordsJsonStr +
                ",\"pagingInfo\":"+
                "{\"total\":"+total+",\"pageIndex\":"+pageIndex+
                "}"+
                "}";
    }
}
