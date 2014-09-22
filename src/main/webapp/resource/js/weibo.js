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
            var wCont = weiboCont.cell($("<a href='javascript:void(0);'>添加到右侧</a>"));
            wCont.click(weiboCont.addWeiboToRight(entity.userName));
            var row = weiboCont.row();
            row.append(wName);
            row.append(wUserId);
            row.append(wFollow);
            row.append(wFollower);
            row.append(wWeibocnt);
            row.append(wCont);
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