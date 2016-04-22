<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<style type="text/css">
body{font-size:12px;position:relative;font-family:Verdana, Geneva, sans-serif;}
a{color:blue;}
ul{margin:0;padding:0;list-style:none;}
#navigation{height:26px;}
#navigation li{float:left;}
#navigation li.show .content{display:block;}
#navigation li a,#navigation li span{text-decoration: none;display:inline-block;border:solid 1px #fff;border-bottom:none;height:26px;padding:0 6px;line-height:20px;overflow:hidden}
#navigation li a:hover,#navigation li.show a.index{color:#ff6026;text-decoration: none;background:#DBF3FE;border-color:#0a0;position:relative;top:0px;z-index:2;}
#container{position:relative;width:800px;margin:0px;}
#navigation li .content{position:absolute;left:0;top:26px;background:#DBF3FE;border:solid 1px #0a0;width:760px;padding:10px;display:none;}
#navigation li .content ul li{margin-right:10px;float:none;display:inline;}
#navigation li .content ul li a{border:none;}
#navigation li .content ul li{width:120px;}
#navigation li .content ul li h3{clear:both;text-align:left;}
</style>
</head>
<body>
<div id="container">
    <ul id="navigation">
        <li>
            <a href="#" class="index">A</a>
            <div class="content">
                <h3>WWWWWWWWWWWWɽ</h3>
                <ul>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                </ul>
                <h3>WWWWWWWWWWWWɽ</h3>
                <ul>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                    <li><a href="#">WWWWWWWWWWWWɽ</a></li>
                </ul>
            </div>
        </li>
        <li>
            <a href="#" class="index">B</a>
            <div class="content">
                <h3>WWWWWWWWWWWWWWWWWW</h3>
                <ul>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                </ul>
                <h3>WWWWWWWWWWWWWWWWWW</h3>
                <ul>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                    <li><a href="#">WWWWWWWWWWWWWWWWWW</a></li>
                </ul>
            </div>
        </li>
        <li><a href="#" class="index">C</a><div class="content">C</div></li>
        <li><a href="#" class="index">D</a><div class="content">D</div></li>
        <li><a href="#" class="index">E</a><div class="content">E</div></li>
        <li><a href="#" class="index">F</a><div class="content">F</div></li>
        <li><a href="#" class="index">G</a><div class="content">G</div></li>
        <li><a href="#" class="index">H</a><div class="content">H</div></li>
        <li><a href="#" class="index">I</a><div class="content">I</div></li>
        <li><a href="#" class="index">J</a><div class="content">J</div></li>
        <li><a href="#" class="index">K</a><div class="content">K</div></li>
        <li><a href="#" class="index">L</a><div class="content">L</div></li>
        <li><a href="#" class="index">M</a><div class="content">M</div></li>
        <li><a href="#" class="index">N</a><div class="content">N</div></li>
        <li><a href="#" class="index">O</a><div class="content">O</div></li>
        <li><a href="#" class="index">P</a><div class="content">P</div></li>
        <li><a href="#" class="index">Q</a><div class="content">Q</div></li>
        <li><a href="#" class="index">R</a><div class="content">R</div></li>
        <li><a href="#" class="index">S[sh]</a><div class="content">S</div></li>
        <li><a href="#" class="index">T</a><div class="content">T</div></li>
        <li><a href="#" class="index">U</a><div class="content">U</div></li>
        <li><a href="#" class="index">V</a><div class="content">V</div></li>
        <li><a href="#" class="index">W</a><div class="content">W</div></li>
        <li><a href="#" class="index">X</a><div class="content">X</div></li>
        <li><a href="#" class="index">Y</a><div class="content">Y</div></li>
        <li><a href="#" class="index">Z[zh]</a><div class="content">Z</div></li>
    </ul>
</div>
</body>
</html>
<script type="text/javascript">
function showAjaxContent(){
    var obj=document.getElementById("navigation");
    var liObj=obj.getElementsByTagName("li");
    var length=liObj.length;
    var currentLiObj;
    for(var i=0;i<length;i++){
        currentLiObj=liObj[i];
        if(currentLiObj.parentNode!=obj){continue;}
        currentLiObj.onclick=function(){
            if(this.className.indexOf("show")<0){
                this.className+=" show";
                clearStyle(this);
            }else{
            this.className=this.className.replace("show","");
            clearStyle(this);
            }
            
        }        
        //currentLiObj.onmouseout=function(){
        //    this.className=this.className.replace("show","");
        //    clearStyle(this);
       // }
    }
    function clearStyle(obj){
        for(var i=0;i<length;i++){
            currentLiObj=liObj[i];
            if(obj!=currentLiObj){
                currentLiObj.className=currentLiObj.className.replace("show","");
            }
        }
    }
}
showAjaxContent();
</script>