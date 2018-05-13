
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
                var infoList=data.split('^');
                $('#name').innerHTML=infoList[1];
                $('#telephone').innerHTML=infoList[2];
                $('#email').innerHTML=infoList[3];
                $('#description').innerHTML=infoList[4];
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
               $('#image').attr('src',data);
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