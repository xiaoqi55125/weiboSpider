package com.abcve.weiboSpider.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhicheng on 14/9/19.
 */
public class TxweiboServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.index(req, resp);
        req.getRequestDispatcher("/WEB-INF/page/layout.jsp").forward(req, resp);
        //super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

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
        req.setAttribute("mainContainer", "/WEB-INF/page/t/tx.jsp");
    }
}
