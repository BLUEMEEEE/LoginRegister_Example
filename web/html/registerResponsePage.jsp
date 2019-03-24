<%--
  Created by IntelliJ IDEA.
  User: BLUEMEEE
  Date: 2019/3/3
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body onload="onLoad()">
<!--<h1>${name},恭喜您注册成功！您的id是${id}</h1>-->
<!--<h1>注册失败，请重试</h1>-->
<script type="text/javascript">
    function onLoad(){
        var status='${status}';
        var h=document.createElement("h1");
        if(status=="1"){
            var text=document.createTextNode("${name},恭喜您注册成功！您的id是${id}");
            h.appendChild(text);
            document.body.appendChild(h);
            var a=document.createElement("a");
            a.appendChild(document.createTextNode("登出"));
            a.setAttribute("href","${pageContext.request.contextPath}/logout");
            document.body.appendChild(a);
        }
        else{
            var text=document.createTextNode("ERROR");
            h.appendChild(text);
            document.body.appendChild(h);
        }

    }
</script>
</body>
</html>
