<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h3>自定义登录页面</h3>
<form action="/login.do" method="post">
    username：<input type="text" name="username"><br>
    password：<input type="password" name="password"><br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="submit" value="submit">
</form>
</body>
</html>