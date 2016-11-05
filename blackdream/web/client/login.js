/**
 * Created by LaineyC on 2015/8/12.
 */
;(function( $, window, undefined ) {
    $(function() {
        var errorTime = 0,
            $alert = $("#alert"),
            $progress = $("#progress"),
            $signIn = $("#sign-in"),
            $username = $("#username"),
            $password = $("#password"),
            $codeGroup = $("#code-group"),
            $code = $("#code"),
            $codeImg =  $("#code-img");

        var status = {progress:0, max:60, step:10, now:0, response:null, submit:false};
        
        function progressRun(){
            $progress.removeClass("hide");
            var hasError = status.response && status.response.error;
            if(status.now < status.max || !status.submit){
                status.now += status.step;
                $progress.find("div").attr("aria-valuenow", status.now).css("width", status.now + "%");
                if(status.now >= 100 && !hasError){
                    window.setTimeout(function(){
                        window.location = "/framework.html";
                    }, 250);
                    return;
                }
            }
            else{

            }
            if(hasError){
                $progress.addClass("hide");
                var error = status.response.error.message;
                status = {progress:0, max:60, step:10, now:0, response:null, submit:false};
                $progress.find("div").attr("aria-valuenow", status.now).css("width", status.now + "%");
                errorTime++;
                $alert.removeClass("hide");
                $alert.text(error);
                /*
                if(errorTime >= 3){
                    $codeGroup.removeClass("hide");
                }
                */
                return;
            }
            window.setTimeout(progressRun, 10);
        }

        var signInFun = function(){
            if(status.submit)
                return;
            var username = $.trim($username.val()),
                password = $.trim($password.val()),
                code = $.trim($code.val());
            if(username === ""){
                $alert.removeClass("hide");
                $alert.text("请填写用户名");
            }
            else if(!/^[a-zA-Z0-9]{4,32}$/.test(username)){
                $alert.removeClass("hide");
                $alert.text("用户名为4-32位数字或字母");
            }
            else if(password === ""){
                $alert.removeClass("hide");
                $alert.text("请填写密码");
            }
            else if(!/^[a-zA-Z0-9]{6,32}$/.test(password)){
                $alert.removeClass("hide");
                $alert.text("密码为6-32位数字或字母");
            }
            /*
             else if(errorTime >= 3 && code === ""){
             $alert.removeClass("hide");
             $alert.text("请填写验证码");
             }
             */
            else{
                $alert.addClass("hide");
                $alert.text("");
                $.ajax({
                    type: "post",
                    dataType:"json",
                    data:"{\"userName\": \"" + username +"\",\"password\":\"" + password + "\"}",
                    contentType:"application/json;charset=UTF-8",
                    url: "/api?method=user.login",
                    beforeSend : function(){
                        status.submit = true;
                        progressRun();
                    },
                    success: function(data){
                        status.response = data;
                        status.submit = false;
                    }
                });
            }
        };

        $signIn.click(signInFun);

        $username.keyup(function(event){
            if(event.which == 13){
                signInFun();
            }
        });

        $password.keyup(function(event){
            if(event.which == 13){
                signInFun();
            }
        });

        /*
        $codeImg.click(function(){
            $(this).find("img").attr("src","http://manage.dingcai.com/verificationCode.jsp?date=" + new Date().getTime());
        });
        */
    });
}( jQuery, window ));