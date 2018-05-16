/**
 * Created by Leonarda on 2018/4/22.
 */

$(function () {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];

    new Vue({
        el:'#user',
        data:{
            username:username
        }
    });



    Vue.component('task',{
        template:'<li> <a href="taskdetails.html"> <img class="am-img-thumbnail am-img-bdrs" v-bind:src="imgaddress" alt=""/> <div class="gallery-title">{{description}}</div> <div class="gallery-desc">{{time}}</div> </a> </li>',
        props:['imgaddress','description','time']
    });
    new Vue({
        el:'#taskList'
    });


    $.get("http://127.0.0.1:8080/checkAllTasks",function (data) {

        var tasknum=0;

        var arr=data.split("!");

        if(data.length==0){
            return;
        }
        else{
            for(x in arr){
                var taskarr=arr[x].split(" ");
                var imgAddress=taskarr[3];
                var description=taskarr[2];
                var partofneeded='需要人数：'+taskarr[4];
                var taskname=taskarr[0];

                $('#taskList').prepend('<li> <a href="taskdetails.html?'+taskname+'"'+'> <img class="am-img-thumbnail am-img-bdrs" src="'+imgAddress+'" alt=""/> <div class="gallery-title">'+description+'</div> <div class="gallery-desc">'+partofneeded+'</div> </a> </li>');

                tasknum=tasknum+1;
            }
        }

        new Vue({
            el:'#tasknum',
            data:{
                tasknum:tasknum
            }
        })
        // for(x in arr){
        //     var taskarr=arr[x].split(" ");
        //     var imgAddress=taskarr[3];
        //     var description=taskarr[2];
        //     var partofneeded='需要人数：'+taskarr[4];
        //     var taskname=taskarr[0];
        //
        //     $('#taskList').prepend('<li> <a href="taskdetails.html?'+taskname+'"'+'> <img class="am-img-thumbnail am-img-bdrs" src="'+imgAddress+'" alt=""/> <div class="gallery-title">'+description+'</div> <div class="gallery-desc">'+partofneeded+'</div> </a> </li>');
        //
        //     tasknum=tasknum+1;
        //
        //     new Vue({
        //         el:'#tasknum',
        //         data:{
        //             tasknum:tasknum
        //         }
        //     })
        // }
    })

});