<%--
  Created by IntelliJ IDEA.
  User: BLUEMEEE
  Date: 2019/3/1
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="bean.User"%><!--引入User类-->
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path ;
    String value=request.getParameter("value");
%>
<html>
<head>
    <title>登录</title>
</head>
<body onload="onLoad()">
    <!--<h1>${sessionScope.user.name},恭喜您登陆成功！</h1>-->
    <!--<h1>登陆失败，您的id或密码输入错误</h1></h1>-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/cookie.js"></script>
    <script type="text/javascript">

        function onLoad(){
            var loginFlag=getCookie("zyq_loginFlag");
            var status='${status}';
            if(status!=null&&status=="1"){
                //登陆成功
                window.alert("${msg}") ;//使用了EL表达式
                var h=document.createElement("h1");
                var text=document.createTextNode("${sessionScope.user.name},恭喜您登陆成功！");
                h.appendChild(text);
                var a=document.createElement("a");
                a.appendChild(document.createTextNode("登出"));
                a.setAttribute("href","${pageContext.request.contextPath}/logout");
                document.body.appendChild(h);
                document.body.appendChild(a);
            }
            else{
                if(loginFlag!=null&&loginFlag=="1"){
                    //登陆成功
                    var h=document.createElement("h1");
                    var text=document.createTextNode("${sessionScope.user.name},欢迎回来！");
                    h.appendChild(text);
                    var a=document.createElement("a");
                    a.appendChild(document.createTextNode("登出"));
                    a.setAttribute("href","${pageContext.request.contextPath}/logout");
                    document.body.appendChild(h);
                    document.body.appendChild(a);
                }
                else{
                    var h=document.createElement("h1");
                    var text=document.createTextNode("ERROR！！！");
                    h.appendChild(text);
                    document.body.appendChild(h);
                }
            }
        }
    </script>

</body>
</html>
