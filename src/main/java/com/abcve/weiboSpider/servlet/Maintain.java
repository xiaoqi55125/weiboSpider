package com.abcve.weiboSpider.servlet;

import com.abcve.weiboSpider.Entity.SinaUser;
import com.abcve.weiboSpider.dao.UserMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Created by zhicheng on 14/9/19.
 */
public class Maintain extends HttpServlet {

    private static final Log logger = LogFactory.getLog(Maintain.class);
    protected static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.index(req, resp);

        //testMybatis() ;
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

        }


    }

    /**
     * deal the first get request
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

    protected void testMybatis() {
        SqlSession sqlSession = getSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        SinaUser user = userMapper.findUserById("1");
        List<SinaUser> sinaUsers = userMapper.findUserByPageIndex(1);
        System.out.println("--->"+user.getUserName());
        logger.info(user.getUserName());
        for(SinaUser sinaUser:sinaUsers){
            System.out.println(sinaUser.getUserid()+":"+sinaUser.getUserName());
        }
        int isAdd = userMapper.addSinaUser("不知道6");
        int isDel = userMapper.deleteOneUser(5);
        logger.info(isAdd+"<------------1就是插入成功"+isDel);
        sqlSession.commit();
    }

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
    private static SqlSessionFactory getSessionFactory() {
        SqlSessionFactory sessionFactory = null;

        String resource = "configuration.xml";
        try {
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources
                    .getResourceAsReader(resource));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sessionFactory;
    }

    protected void responseJsonData(HttpServletResponse resp , String data){
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        try {
            resp.getWriter().print(data);
            logger.info("###");
            logger.info(data);
        }catch(Exception e){
            String error = generateErrorJSONStr(e.getMessage());
            try {
                resp.getWriter().print(error);
            } catch (IOException ex) {

            }
        }
    }

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

    //apis
    //http://api.weibo.com/2/statuses/timeline_batch.json?source=211160679&screen_names=黄健翔,一个汉字两个字节,海信手机官方微博

}
