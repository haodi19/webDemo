<%--
  Created by IntelliJ IDEA.
  User: 86135
  Date: 2021/7/26
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ch">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <style>
        * {
            margin: auto;
        }

        #title {
            color: red;
            font-size: 60px;
            text-align: center;
            line-height: 70px;
        }

        #x {
            height: 75px;
            width: 400px;
            border: 2px solid red;
            margin-top: 20px;
        }

        #t {
            margin: auto;
        }

        .left {
            height: 30px;
            text-align: right;
        }

        .right {
            height: 30px;
            padding-left: 10px;
            box-sizing: border-box;
        }

        input[type='submit'] {
            margin-top: 10px;
            margin-left: 100px;
        }

        .checkbox {
            padding-left: 10px;
            box-sizing: border-box;
        }

        div[id='error'] {
            text-align: center;
        }

        td[id='error'] {
            color: red;
            font-size: 12px;
        }

        td[id='register'] {
            padding-left: 10px;
        }

    </style>

</head>
<body>
<div id="x">
    <div id="title"> 登录页面</div>
</div>
<br>
<form action="${pageContext.request.contextPath}/loginServlet" method="post" id="form">
    <table id="t">
        <tr>
            <td class="left"><label for="u">用户名</label></td>
            <td class="right"><input type="text" name="username" value="${sessionScope.login_username}"
                                     placeholder="请输入用户名" id="u"></td>
            <td id="register"><a href="${pageContext.request.contextPath}/register.jsp">注册</a></td>
        </tr>
        <tr>
            <td class="left"><label for="p">密码 </label></td>
            <td class="right"><input type="password" name="password" value="${sessionScope.login_password}"
                                     placeholder="请输入密码" id="p"></td>
        </tr>
        <tr>
            <td class="left">选项</td>
            <td class="checkbox"><input type="checkbox" name="choice" value="选项1" id="c1">选项1
                <input type="checkbox" name="choice" value="选项2" id="c2">选项2
            </td>
            <td><a href="${pageContext.request.contextPath}/downloadServlet?filename=demoImage.jpg">资源下载</a></td>
        </tr>
        <tr>
            <td class="left">验证码</td>
            <td class="right">
                <label for="c_code"></label><input type="text" name="checkCode" placeholder="请输入验证码" id="c_code">
            </td>
            <td>
                <img src="${pageContext.request.contextPath}/checkCodeServlet" id="checkCodeImg">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="登录" id="submit">
            </td>
            <td id="error">
                ${sessionScope.registerResult}
                <c:if test="${empty sessionScope.registerResult}">${sessionScope.loginError}<% session.removeAttribute("registerResult"); %>
                </c:if>
                <c:if test="${!empty sessionScope.registerResult}"><% session.removeAttribute("registerResult"); %>
                </c:if>

                <%--<% String loginError = (String) request.getSession().getAttribute("loginError");
                    if (loginError != null) {
                        out.print(loginError);
                    }
                %>--%>
            </td>
        </tr>
    </table>
</form>

</body>
<foot>
    <script>
        var checkCodeImg = document.getElementById("checkCodeImg");
        checkCodeImg.onclick = function () {
            checkCodeImg.src = "/web/checkCodeServlet?" + new Date().getTime();
        }
    </script>
</foot>
</html>
