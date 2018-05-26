/*
* 初始化，包括个人信息和头像
 */
$(function() {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];

    new Vue({
        el:'#username',
        data:{
            username:username
        }
    });

    var user=new User(username,'aaa',0,'aaa','aaa','aaa','aaa','aaa');
    var userJson=JSON.stringify(user);

    $.ajax({
        type:'POST',
        data:userJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/showUserInformation',
        success:function (data) {
            if(data=='no'){
                return;
            }else{
                //alert(data);
                var infoList=data.split(' ');
                //alert(infoList[0]);
                $('#name').html(infoList[1]);
                $('#telephone').html(infoList[2]);
                $('#email').html(infoList[3]);
                $('#description').html(infoList[4]);
            }
        },
        error:function(e){
            alert("error");
        }
    });

    $.ajax({
        type:'POST',
        data:userJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/showUserImg',
        success:function (data) {
           if(data=='no') {
               return;
           }else{
               $('#image').attr('src',"data:image/jpeg;base64,"+data);
               //$('#image').attr('src',data);
           }
        },
        error:function(e){
            alert("error");
        }
    });
});

function User(username,password,point,name,email,phone,description,taskAddress) {
    this.username=username;
    this.password=password;
    this.point=point;
    this.name=name;
    this.email=email;
    this.phone=phone;
    this.description=description;
    this.taskAddress=taskAddress;
}