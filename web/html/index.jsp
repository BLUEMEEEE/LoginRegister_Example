<%--
  Created by IntelliJ IDEA.
  User: BLUEMEEE
  Date: 2019/3/1
  Time: 13:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head >
    <title>首页</title>
</head>
<body onload="isLogin()">
<div>
    <form action="login" method="post" name="login" >
        登录账户（id）:
        <input type="text" name="id"/>
        密码：
        <input type="password" name="password"/>
        <button onclick="return loginSubmit()" >登录</button>
    </form>
</div>
<div>
    <form action="register" method="post" name="register">
        用户名:
        <input type="text" name="name"/>
        密码：
        <input type="password" name="password"/>
        <input type="submit" onclick="return registerSubmit()" value="登录"/>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/cookie.js"></script>
<script type="text/javascript">
    //判断是否登录过，登录过则直接跳转
    function isLogin(){
        var loginFlag=getCookie("zyq_loginFlag");
        var status='${status}';
        if(status=="0"){
            alert('${msg}');
        }
        if(loginFlag=="1"){
            alert("TEST：您已登陆，将为您自动跳转！");
            window.location.href="${pageContext.request.contextPath}/html/loginResponsePage.jsp";
        }
    }
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/md5.js"></script>
<script>
    //TODO:加密方式需升级
    function loginSubmit(){

        var theForm=document.login;//document.forms返回所有的form元素，坐标0返回第一个
        if(theForm.id.value!==''&&theForm.password.value!==''){
            theForm.password.value=hex_md5(theForm.password.value);//hex_md5(theForm.password)
            theForm.submit();
            return true;
        }
        else{
            alert("id或密码不能为空");
            return false;
        }
    }

    function registerSubmit(){
        var theForm=document.register;//document.forms返回所有的form元素，坐标0返回第一个
        if(theForm.name.value!=''&&theForm.password.value!=''){
            theForm.password.value=hex_md5(theForm.password.value);
            theForm.submit();
            return true;
        }
        else{
            alert("姓名或密码不能为空");
            return false;
        }
    }
</script>
</body>

</html>
