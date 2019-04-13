<%--
  Created by IntelliJ IDEA.
  User: shiyufeng
  Date: 2019-04-06
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login登录页面</title>
</head>
<body>
    <form method="get" action="/user/login">
        用户名：<input type="text" name="name" /><br/>
        密码：<input type="text" name="pwd" /><br/>
        <input type="submit" value="login" />
    </form>
</body>
</html>
