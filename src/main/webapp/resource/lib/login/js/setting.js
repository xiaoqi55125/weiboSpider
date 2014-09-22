/**
 * Created by yanghua on 4/22/14.
 */
 $(function () {
     /*
      *fixed bug: if the document is not ready,
      *the request send by src will course session param lost
      */
     $("#captcha_div").append("<img src=\"/captchaImg\" class=\"captcha\" />");

     $(".container .form-signin #refreshBtn").click(function () {
         var jqObj = $(".container .form-signin .captcha");
         jqObj.prop("src",randomImgSrc(jqObj));
     });

     $(".container .form-signin .captcha").click(function () {
         var jqObj = $(".container .form-signin .captcha");
         jqObj.prop("src",randomImgSrc(jqObj));
     });

     $("#div_tip").hide();

 });


 /**
  * js submit form for login
  * @return {null}
  */
 function postChangePassForm () {
     var newpwd1 = $("#newPassword1").val();
     var newpwd = $("#newPassword").val();
     var oldpwd = $("#oldPassword").val();
     if (newpwd.length === 0) {
         return false;
     }
     if (oldpwd.length === 0) {
         return false;
     }
     if(newpwd != newpwd1 ){
         alert("两次密码不一致");
     }

     var crypedPwdNew = CryptoJS.SHA256(newpwd)+"";
     var crypedPwdOld = CryptoJS.SHA256(oldpwd)+"";

     $.ajax({
         url     : "/setting.do",
         type    : "POST",
         async   : false,
         cache   : false,
         data    : {
             "newPassword"      : crypedPwdNew,
             "oldPassword"        :crypedPwdOld
         },
         success : function (statusCode) {
             console.log(statusCode.Result);
             if (statusCode.Result == 'OK') {
                    alert("密码修改成功!");
                     window.location="/setting.do";
                 } else {
                     showTip("密码修改出错 请检查旧密码输入是否正确");
                 }

         },
         error   : function () {
             alert('失败!');
         }
     });
     return false;
 }

 /**
  * shwo login error tips
  * @param  {string} statusCode status code
  * @return {null}
  */
 function showTip (statusCode) {
     if (statusCode === "0") {
         $("#span_tipMsg").text("登陆失败");
     } else if (statusCode === "2") {
         $("#span_tipMsg").text("用户不存在");
     } else if (statusCode === "3") {
         $("#span_tipMsg").text("服务器错误");
     } else if (statusCode === "4") {
         $("#span_tipMsg").text("验证码错误");
     } else if (statusCode === "5") {
         $("#span_tipMsg").text("认证信息为空或存在非法字符");
     }else{
         $("#span_tipMsg").text(statusCode);
     }

     $("#div_tip").fadeIn(1000);
     $("#div_tip").fadeOut(1000);
 }

