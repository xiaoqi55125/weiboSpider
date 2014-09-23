var weiboCont = {
    cell: function(item) {
        return $("<td></td>").html(item);
    },

    row: function() {
        return $("<tr></tr>");
    },
    addWeiboToRight: function(name) {
        return function () {
            addWeiboToRight(name);
        }
    },
    delUserFromList: function(name, id){
        return function(){
            delUserFromList(name, id);
        }
    }
};
function getSinaUserByPageIndex(pageIndex){
    $.ajax({
        url:'/home',
        type:'POST',
        data:{'action':'sinaUserList',
            'pageIndex':pageIndex},
        success:function(data){
            renderDataToTable(data);
        }
    });
}
var closeDialog = function () {
    $.unblockUI();
};

function renderDataToTable(data){
    if(data.Result === 'OK'){
        $("#sinaUserListView").html("");
        data.Records.forEach(function(entity){
            console.log(entity);
            var wName = weiboCont.cell(entity.userName);
            var wUserId = weiboCont.cell(entity.realid);
            var wFollow = weiboCont.cell(entity.follow);
            var wFollower = weiboCont.cell(entity.follower);
            var wWeibocnt = weiboCont.cell(entity.weibocnt);
            var $addToRight = $("<a href='javascript:void(0);'>添加到右侧</a>");
            var $delUser = $("<a href='javascript:void(0);'>删除用户</a>");
            $addToRight.click(weiboCont.addWeiboToRight(entity.userName));
            $delUser.click(weiboCont.delUserFromList(entity.userName,entity.userid));
            var wCont = weiboCont.cell($addToRight);
            var wDel = weiboCont.cell($delUser);
            //wCont.click(weiboCont.addWeiboToRight(entity.userName));
            var row = weiboCont.row();
            row.append(wName);
            row.append(wUserId);
            row.append(wFollow);
            row.append(wFollower);
            row.append(wWeibocnt);
            row.append(wCont);
            row.append(wDel);
            $("#sinaUserListView").append(row);
        });
        var pageInfo = data.pagingInfo;
        $('#paginator_div').pagination('destroy');
        if (pageInfo.total>10) {
            $('#paginator_div').pagination({
                items: pageInfo.total,
                itemsOnPage: 10,
                currentPage: pageInfo.pageIndex+1,
                cssStyle: 'light-theme',
                onPageClick: function(pageNum){
                    getSinaUserByPageIndex(pageNum-1);
                }
            });
        }else{
            $('#paginator_div').pagination('destroy');
        }
    }
}

//addWeiboToRight
function addWeiboToRight(name){
    $("#train_target").append(name+',');
}

function addWeiboUserByScreenName(){
    $("#sinaUserScreenName").val("");
    $.blockUI({
        theme:true,
        title: '添加新的新浪微博用户',
        message : $('#dialogContainer'),
        themedCSS: {
            width: '300px',
            top: '20%',
            left: '30%'
        },
        onOverlayClick: $.unblockUI
    });
}
function addSinaUserInfoToDatabase(){
    $.ajax({
        url:'/home',
        type:'POST',
        data:{
            'action':'insertSinaUser',
            'sinaUserScreenName':$("#sinaUserScreenName").val()
        },
        success:function(data){
            console.log("插入成功~~");
            closeDialog();
            getSinaUserByPageIndex(0);
        },
        error:function() {
        console.log('error');
         }
    })
}
function delUserFromList(name, id){
    var agree=confirm("确认删除"+name+"吗?");
    alert(id);
    if (agree)
        $.ajax({
            url:'/home',
            type:"POST",
            data:{
                'action':'delUser',
                'userid':id},
            success:function(){
                getSinaUserByPageIndex(0);
            },
            error:function(){
                console.log('err');
            }
        })
}

function updateAllSinaUser(){
    $.ajax({
        url:'/home',
        type:"POST",
        data:{
            'action':'updateAllSinaUser'},
        success:function(){
            alert("succ");
            getSinaUserByPageIndex(0);
        },
        error:function(){
            console.log('err');
        }
    })
}

