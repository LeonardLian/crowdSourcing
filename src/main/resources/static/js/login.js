/**
 * Created by Leonarda on 2018/4/21.
 */
function login(){

    var username=$('#username').val();
    var password=$('#password').val();
    var user=new User(username,password,'0','aaa');

    var userJson=JSON.stringify(user);
    $.ajax({
        type:'POST',
        data:userJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/login',
        success:function (data) {
            if(data=="登录成功"){
                var mainpage='mainpage.html'+'?'+username;
                window.location.href=mainpage;
            }else{
                alert(data);
            }
        },
        error:function(e){
            alert("error");
        }
    });

    function User(username,password,point,taskAddress) {
        this.username=username;
        this.password=password;
        this.point=point;
        this.taskAddress=taskAddress;
    }
}

