<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>微博爬虫</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width"/>
    <link rel="stylesheet" href="resource/css/css.css"/>
    <link rel="stylesheet" href="resource/css/weibo_tx_css.css"/>

</head>
<body>
<div class="header">
    <jsp:include page='<%= (String)request.getAttribute("header") %>'/>
</div>
<div class="container">
    <div class="leftbar">
        <jsp:include page='<%= (String)request.getAttribute("leftUserInfo") %>'/>
    </div>
    <div class="mainbody">
        <%--<jsp:include page='<%= (String)request.getAttribute("secondLevelMenu") %>'/>--%>
        <jsp:include page='<%= (String)request.getAttribute("mainContainer") %>'/>
    </div>
</div>
<div class="footer"></div>
</body>
</html>