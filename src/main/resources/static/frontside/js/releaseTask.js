/**
 * Created by Leonarda on 2018/4/27.
 */
/*
TODO 删除上传的图片
TODO 一次性传所有图片
TODO 保存任务相关信息
 */



/*var fileEle=document.getElementById('file');
var imgEle=document.getElementById('img1');
fileEle.onchange=function (e) {
    var file1 = e.target.files[0];
    var reader = new FileReader;
    var url2 = reader.readAsDataURL(file1);
    reader.onload = function () {
        imgEle.src = reader.result;
    }

}

$(function () {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[2];

    new Vue({
        el:'#user',
        data:{
            username:username
        }
    });
})

function release() {
    var fileEle=document.getElementById('xfile');
    var imgEle=document.getElementById('img1');
    fileEle.onchange=function (e) {
        var file1 = e.target.files[0];
        var reader = new FileReader;
        var url2 = reader.readAsDataURL(file1);
        alert(reader.result);
        reader.onload = function () {
            imgEle.src = reader.result;
        }
    }
}*/

//即时显示上传的图片
$('#image').on('change',function () {
    var fileObj = document.getElementById("image");
    var fileList = fileObj.files;
    for(var i=0;i<fileList.length;i++){
        var src = window.URL.createObjectURL(this.files[i]);
        $('#showPic').prepend('<li> <img class="am-img-thumbnail am-img-bdrs" src="'+src+'" alt="failed" width="1000" /> </li>');
    }
})

function release() {
    saveImagesOfTask();
    saveInfoOfTask()
}
//???????????这里能不能一次性传所有图片，但不确定图片数量
function saveImagesOfTask() {
    var img_file=document.getElementById("image");
    var fileList=img_file.files;
    for(var i=0;i<fileList.length;i++) {

        var fileObj = img_file.files[i];
        //var url=decodeURI(window.location.href);
        //var username=url.split("?")[1];
        var formData = new FormData();
        formData.append("classIcon", fileObj);
        //formData.append("classDescribe",username);

        $.ajax({
            url: "http://127.0.0.1:8080/saveTaskImg",
            type: 'POST',
            data: formData,
            async: false,
            processData: false,
            contentType: false,
            success: function (e) {
                alert("success");
            },
            error: function (e) {
                alert("error");
            }
        });
    }
}

function saveInfoOfTask(){

}