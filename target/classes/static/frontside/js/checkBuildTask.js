/**
 * Created by Leonarda on 2018/4/23.
 */
$(function () {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[2];

    new Vue({
        el:'#user',
        data:{
            username:username
        }
    });

    // Vue.component('joinedtask',{
    //     template:'<li> <a href="workspace.html"> <img class="am-img-thumbnail am-img-bdrs" v-bind:src="imgaddress" alt=""/> <div class="gallery-title">{{description}}</div> <div class="gallery-desc">{{time}}</div> </a> </li>',
    //     props:['imgaddress','description','time']
    // });
    // new Vue({
    //     el:'#taskList',
    // });

    var user=new User(username,'0','0','0');
    var userJson=JSON.stringify(user);

    $.ajax({
        type:'POST',
        data:userJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/checkBuildedTasks',
        success:function (data) {

            var tasknum=0;

            var tasklist=data.split("!");
            for(x in arr){
                var taskarr=arr[x].split(" ");
                var imgAddress=taskarr[3];
                var description=taskarr[2];
                var partofneeded='已参加人数：'+taskarr[4];
                var taskname=taskarr[0];

                $('#buildedtaskList').prepend('<li> <a href="workerArts.html?'+taskname+'"'+'> <img class="am-img-thumbnail am-img-bdrs" src="'+imgAddress+'" alt=""/> <div class="gallery-title">'+description+'</div> <div class="gallery-desc">'+'</div> </a> </li>');
            }

            tasknum=tasknum+1;
            new Vue({
                el:'#tasknum',
                data:{
                    tasknum:tasknum
                }
            })

        },

        error:function(e){

            new Vue({
                el:'#tasknum',
                data:{
                    tasknum:0
                }
            })

            alert("你暂时未发布任何任务");
        }
    });


    function User(username,password,point,taskAddress) {
        this.username=username;
        this.password=password;
        this.point=point;
        this.taskAddress=taskAddress;
    }
})
