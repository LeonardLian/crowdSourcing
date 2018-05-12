/**
 * Created by Leonarda on 2018/3/22.
 */
var fileEle=document.getElementById('file');
var imgEle=document.getElementById('exampleImg');
fileEle.onchange=function (e) {
    var file1=e.target.files[0];
    var reader=new FileReader;
    var url2=reader.readAsDataURL(file1);
    reader.onload=function () {
        imgEle.src=reader.result;
        getSquareDot();
        getCurveDot();
    }
};

function getSquareDot() {
    var result;
    $.get("http://127.0.0.1:8080/sendSquareDot",function (data) {
        var ts=data.split("。");
        result=eval('(' + ts[0] + ')');
        var oSquareDotDiv=document.createElement("div");
        oSquareDotDiv.style.top=result.y+'px';
        oSquareDotDiv.style.left=result.x+'px';
        oSquareDotDiv.style.width=result.width;
        oSquareDotDiv.style.height=result.height;
        oSquareDotDiv.style.background='transparent';
        oSquareDotDiv.style.border='3px solid black';
        oSquareDotDiv.style.position='absolute';
        document.body.appendChild(oSquareDotDiv);
    })
}

function getCurveDot() {
    $.get("http://127.0.0.1:8080/sendCurveDot",function (data) {
        var arr=data.split("!");
        for(var i=0;i<arr.length;i++){
            //alert(arr[0]);
            var jsondot=eval('(' + arr[i] + ')');
            var oCurvediv=document.createElement("div");
            oCurvediv.style.top=jsondot.y+'px';
            oCurvediv.style.left=jsondot.x+'px';
            oCurvediv.style.background='black';
            oCurvediv.style.width='2px';
            oCurvediv.style.height='2px';
            oCurvediv.dragging='false';
            oCurvediv.style.position='absolute';
            document.body.appendChild(oCurvediv);
        }
    })
}
