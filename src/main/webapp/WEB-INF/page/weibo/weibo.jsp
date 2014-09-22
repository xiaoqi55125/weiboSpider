<%--
  Created by IntelliJ IDEA.
  User: zhicheng
  Date: 14/9/19
  Time: 下午12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="/resource/js/weibo.js" type="text/javascript"></script>
    <title></title>
    <script>
        $(function($) {
            getSinaUserByPageIndex(0);

        });
    </script>
</head>
<body>
<div class="wholePanel">
    <div class=" leftPanel">
        <div style="font-size: 18px;padding-top:5px;padding-bottom: 5px;background-color: #f8f8f8">
            <span >已存联系人</span>
            <span style="float: right"><a href="javascript:addWeiboUserByScreenName()" >添加用户</a></span>
        </div>
        <div id="tabCot_product_1" class="tabCot">
            <table id="bookListStockOut"  class="tabindex" width="100%" border="0" cellpadding="0" cellspacing="0">
                <thead>
                <tr bgcolor="#f8f8f8">
                    <th width="20%"  scope="col"><div align="center">微博名称</div></th>
                    <th width="20%" scope="col"><div align="center">微博隐藏ID</div></th>
                    <th width="10%" scope="col"><div align="center">微博总数</div></th>
                    <th width="10%"  scope="col"><div align="center">关注人数</div></th>
                    <th width="10%" scope="col"><div align="center">粉丝人数</div></th>
                    <th width="20%" scope="col"><div align="center">操作</div> </th>
                </tr>
                </thead>
                <tbody id='sinaUserListView'>
                </tbody>
            </table>

            <div class="fanye">
                <div id='paginator_div' style="float:right;padding-top: 15px;" ></div>
            </div>
        </div>
    </div>
    <div class="rightPanel">
        <textarea id="train_target" name="train_target" class="field textarea small" spellcheck="true" rows="10" cols="50" tabindex="9" onkeyup=""></textarea>
    </div>
</div>
</body>
<div id="dialogContainer" style="display: none;" >
        <div>
            <input id="sinaUserScreenName" name="sinaUserScreenName" type="text" tabindex="1"/>
            <input id="okSinaUserBtn" name="okBtn" onclick="addSinaUserInfoToDatabase()" type="button" value="添加" tabindex="2"/>
            <input type="button" id="noBtn" onclick="closeDialog()" value="取消"/>
        </div>
</div>
</html>
