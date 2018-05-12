/**
 * Created by Leonarda on 2018/4/27.
 */

var fileEle=document.getElementById('file');
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
}