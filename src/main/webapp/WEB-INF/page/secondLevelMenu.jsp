<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(document).ready(function () {
        registerFirstLevelMenuEvents();
        highlightCurrentMenu();
        registerSecondLevelMenuEvents();
    });

    var registerSecondLevelMenuEvents = function () {
        $('.rig_nav > li').each(function (index) {
            $(this).mouseover(function () {
                $('.rig_nav > li').removeClass('rig_seleli');
                $(this).addClass('rig_seleli');
            });
        });
    };

    var hoverSecondMenu = function (parentId, entered) {
        $('.rig_nav > li').hide();
        $('.rig_nav > li').removeClass('rig_seleli');
        var $rightParent = $('.rig_nav > li[parentid=' + parentId + ']');
        $rightParent.show();
        $rightParent.first().addClass('rig_seleli');
//        if($rightParent.size() == 0){
//            $('div.currmenu').hide();
//        }else{
//            $('div.currmenu').show();
//        }
    };

    var registerFirstLevelMenuEvents = function () {
        $('.nav > li').each(function (index) {
            $(this).mouseover(function () {
                $('.nav > li').removeClass('seleli');
                $(this).addClass('seleli');
                hoverSecondMenu($(this).attr('id'), true);
            });
//            var $rightParent = $('.rig_nav > li[parentid=' + $(this).attr('id') + ']');
//            if($rightParent.size() == 0){
//                $('div.currmenu').hide();
//            }else{
//                $('div.currmenu').show();
//            }
        });
    };

    var highlightCurrentMenu = function () {
        var uri = window.location.pathname;

        $('.rig_nav > li').each(function () {

            var currentHref = $(this).children().first().prop('href');
            var lastIdx = currentHref.lastIndexOf("/");
            var currentUri = currentHref.substring(lastIdx, currentHref.length);

            console.log("uri : " + uri);
            console.log("currentUri : " + currentUri);
            var parentId = $(this).attr('parentid');
            if(uri == currentUri && parentId != -1) {

                //for parent
                $('.nav > li').removeClass('seleli');
                $('#' + parentId).addClass('seleli');

                $('.rig_nav > li').hide();
                $('.rig_nav > li').removeClass('rig_seleli');
                var $rightParent = $('.rig_nav > li[parentid=' + parentId + ']');
                $rightParent.show();
                $(this).addClass('rig_seleli');

                return;
            }
        });

    };

</script>

<div class="currmenu">
    <ul class="rig_nav">
        <c:forEach var="secondModule" items="${secondLevelModules}">
            <li id="${secondModule.moduleCode}" parentid="${secondModule.parentModule}" style="display: none;">
                <a href="${secondModule.linkUrl}">${secondModule.moduleName}</a>
            </li>
        </c:forEach>

        <%--<li><a href="/permission.do">权限管理</a></li>--%>
        <%--<li><a href="/role.do">角色管理</a></li>--%>
        <%--<li><a href="/department.do">部门管理</a></li>--%>
        <%--<li><a href="/expenditure.do">支出</a></li>--%>
        <%--<li><a href="//reimbursement.do">报销</a></li>--%>
    </ul>
</div>
