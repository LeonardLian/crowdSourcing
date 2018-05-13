
$(function() {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];

    new Vue({
        el:'#username',
        data:{
            username:username
        }
    });

    /*$.ajax({
        type:'POST',
        data:username,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/getSelfinfo',
        success:function (info) {
            var arr=data.split("###");

            var name=arr[0];
            var email=arr[1];
            var telephone=arr[2];
            var points=arr[3];
            var intro=arr[4];
            var image=arr[5];

            new Vue({
                el:'#info',
                data:{
                    name:name,
                    email:email,
                    telephone:telephone,
                    points:points,
                    intro:intro,
                    image:'<img class="am-img-circle am-img-thumbnail" src=image alt="" width="1000"/>'
                }
            });
        },
        error:function(e){
            alert("error");
        }
    });

    function User(username,password,point,taskAddress,name,email,telephone,intro) {
    }*/
})



function editClick() {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var editor='selfpageeditor.html'+'?'+username;
    window.location.href=editor;
}

