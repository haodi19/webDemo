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
  <title>修改信息</title>
  <style>
    * {
      margin: auto;
    }

    #title {
      color: #3e8f3e;
      font-size: 60px;
      text-align: center;
      line-height: 70px;
    }

    #x {
      height: 75px;
      width: 400px;
      border: 2px solid #3e8f3e;
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

    .radio {
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
  <div id="title"> 修改页面</div>
</div>
<br>
<form action="${pageContext.request.contextPath}/updateServlet?currentPage=${sessionScope.UsersToShow.currentPage}" method="post" id="form" onsubmit="return check()">
  <table id="t">
    <tr>
      <td class="left"><label for="u">用户名</label></td>
      <td class="right"><input type="text" name="update_username" value="${sessionScope.originalUser.username}"
                               id="u" disabled></td>
    </tr>
    <tr>
      <td class="left"><label for="p">密码 </label></td>
      <td class="right"><input type="password" name="update_password" value="${sessionScope.originalUser.password}"
                               placeholder="请输入新密码" id="p"></td>
    </tr>
    <tr>
      <td class="left"><label for="rp">确认密码 </label></td>
      <td class="right"><input type="password" name="r_password" value="${sessionScope.originalUser.password}"
                               placeholder="请确认密码" id="rp"></td>
    </tr>
    <tr>
      <td class="left">性别</td>
      <td class="radio">
        <c:if test="${empty sessionScope.originalUser.gender || sessionScope.originalUser.gender.name()=='MALE'}">
          <input type="radio" name="update_gender" value="male" id="s1" checked>男
          <input type="radio" name="update_gender" value="female" id="s2">女
        </c:if>
        <c:if test="${sessionScope.originalUser.gender.name()=='FEMALE'}">
          <input type="radio" name="update_gender" value="male" id="s1">男
          <input type="radio" name="update_gender" value="female" id="s2" checked>女
        </c:if>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit" value="修改" id="submit">
      </td>
    </tr>
  </table>
  <input type="hidden" name="update_id" value="${sessionScope.originalUser.id}">
</form>

</body>
<foot>
  <script>


    function check() {
      var username = document.getElementById("u").value;
      var password = document.getElementById("p").value;
      var re_password = document.getElementById("rp").value;
      var password_exp = /^\w{6,16}$/;
      if (password !== re_password) {
        alert("两次输入密码不一致");
        return false;
      }
      if (!password_exp.test(password)) {
        alert("密码格式错误");
        return false;
      }
    }

  </script>
</foot>
</html>
