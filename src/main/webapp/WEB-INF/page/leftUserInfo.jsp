<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" session="true" isELIgnored="false" %>

<div class="lm01"><img class="peptx" src="resource/images/tximg.jpg"/>

    <div class="pepdet">
        <p class="pepname">${sessionScope.userInfo.userName}</p>

        <p>${userInfo.deptName}</p>

        <p>${userInfo.positionName}</p>
    </div>
    <div class="clear"></div>
</div>
<div class="lm02">
    <div class="title"><img class="icon" src="resource/images/dataicon.jpg"/>

        <h2>说明</h2>
    </div>
    <div class="detail">
        <%--<img class="" src="resource/images/kj_01.jpg"/>--%>
        <p>新浪微博:</p>
        <p>腾讯微博:</p>
    </div>
</div>
<%--<div class="lm03">--%>
    <%--<div class="title">--%>
        <%--<img style="padding-right:5px;" class="icon" src="resource/images/weaicon.jpg"/>--%>

        <%--<h2>天气</h2>--%>
    <%--</div>--%>
    <%--<div class="detail">--%>
        <%--&lt;%&ndash;<img class="" src="resource/images/kj_02.jpg" /> &ndash;%&gt;--%>
        <%--<iframe name="weather_inc" src="http://cache.xixik.com.cn/4/nantong/" charset="gb2312" width="200" height="200"--%>
                <%--frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>--%>
    <%--</div>--%>
<%--</div>--%>


