/**
 * mxf
 */
var c = document.getElementById("myCanvas");
var ctx = c.getContext("2d");
var img = new Image();
img.src = 'assets/img/wyf.jpeg';
//img.src = 'assets/i/examples/loginPage.png';

c.width = 750;
c.height = 550;
var FitWidth = 750;
var FitHeight = 550;

img.onload = function() {
    var _width = img.width;
    var _height = img.height;
    if(_width <= FitWidth && _height <= FitHeight){
        var offset_w = (FitWidth - _width)/2;
        var offset_h = (FitHeight - _height)/2;
        ctx.drawImage(img, offset_w, offset_h, _width, _height);

    }
    else {
        if (_width / _height >= FitWidth / FitHeight) {
            if (_width > FitWidth) {
                img.width = FitWidth;
                img.height = (_height * FitWidth) / _width;
            }
            else{
                img.width = _width;
                img.height = _height;
            }
        }
        else {
            if (_height > FitHeight) {
                img.height = FitHeight;
                img.width = (_width * FitHeight) / _height;
            }
            else{
                img.width = _width;
                img.height = _height;
            }
        }
        // img.width = 750;
        // img.height = 550;
        ctx.drawImage(img, 0, 0, 750, 550);
    }
}