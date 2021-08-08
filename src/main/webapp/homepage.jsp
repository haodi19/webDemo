<%@ page import="cn.domain.User" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: 86135
  Date: 2021/7/27
  Time: 0:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户主页</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="css/bootstrap.min.css"
          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

    <style>
        table[id='inf'] {
            margin: auto;
            width: 50%;
        }

        .table {
            width: 20%;
        }

        tr, th {
            text-align: center;
        }

        div[id='title'] {
            text-align: center;
            font-family: "宋体", serif;
        }

        div[id='table_title'] {
            height: 75px;
            width: 400px;
            border: 2px solid darkseagreen;
            margin: auto;
            text-align: center;
            font-family: "宋体", serif;
        }

        .paging {
            text-align: center;
        }

        .form-inline {
            text-align: center;
        }

        .form-group {
            padding: 10px;
        }

        .form-control {
            margin: 10px;
        }

    </style>
</head>
<body>

<div id="title"><h1>欢迎登陆，${sessionScope.loginUser.username}</h1></div>

<br><br>

<div id="table_title" class="bg-success"><h1>全部用户信息</h1></div>
<br><br>

<form action="${pageContext.request.contextPath}/showUsersWithPagingServlet" class="form-inline" method="post">
    <div class="form-group">
        <label for="exampleInputName1">编号</label>
        <input type="text" class="form-control" id="exampleInputName1" name="id" value="${sessionScope.condition.id[0]}"
               placeholder="请输入id">
    </div>
    <div class="form-group">
        <label for="exampleInputName2">用户名</label>
        <input type="text" class="form-control" id="exampleInputName2" name="username"
               value="${sessionScope.condition.username[0]}" placeholder="请输入用户名">
    </div>
    <div class="form-group">
        <label for="exampleInputPassword2">密码</label>
        <input type="text" class="form-control" id="exampleInputPassword2" name="password"
               value="${sessionScope.condition.password[0]}" placeholder="请输入密码">
    </div>
    <div class="radio">
        <c:if test="${(empty sessionScope.condition.gender[0])||(sessionScope.condition.gender[0]=='x')}">
            <input type="radio" name="gender" value="x" checked> 不限
            <input type="radio" name="gender" value="male"> 男
            <input type="radio" name="gender" value="female"> 女
        </c:if>
        <c:if test="${sessionScope.condition.gender[0]=='male'}">
            <input type="radio" name="gender" value="x"> 不限
            <input type="radio" name="gender" value="male" checked> 男
            <input type="radio" name="gender" value="female"> 女
        </c:if>
        <c:if test="${sessionScope.condition.gender[0]=='female'}">
            <input type="radio" name="gender" value="x"> 不限
            <input type="radio" name="gender" value="male"> 男
            <input type="radio" name="gender" value="female" checked> 女
        </c:if>

    </div>
    <button type="submit" class="btn btn-default" style="margin: 15px">搜索</button>
</form>

<br>

<table class="table table-bordered table-hover" id="inf">
    <tr class="warning">
        <th class="table">编号</th>
        <th class="table">用户名</th>
        <th class="table">密码</th>
        <th class="table">性别</th>
        <th class="table">操作</th>
    </tr>
    <c:forEach items="${sessionScope.UsersToShow.itemsInThisPage}" var="user" varStatus="state">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.gender.name()=="MALE"?"男":"女"}</td>
            <td>
                <button type="button" class="btn btn btn-danger" onclick="deleteUser(${user.id})">删除</button>
                <button type="button" class="btn btn btn-info"
                        onclick="updateUser(${user.id},'${user.username}','${user.password}','${user.gender.name()}')">
                    修改
                </button>
            </td>
        </tr>
    </c:forEach>

</table>

<br>

<nav aria-label="Page navigation" class="paging">
    <ul class="pagination">
        <c:if test="${sessionScope.UsersToShow.currentPage==1}">
            <li class="disabled">
                <a href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.UsersToShow.currentPage!=1}">
            <li>
                <a href="${pageContext.request.contextPath}/showUsersWithPagingServlet?currentPage=${sessionScope.UsersToShow.currentPage-1}&itemsPerPage=5&id=${sessionScope.condition.id[0]}
&username=${sessionScope.condition.username[0]}&password=${sessionScope.condition.password[0]}&gender=${sessionScope.condition.gender[0]}"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>
        <c:forEach begin="1" end="${sessionScope.UsersToShow.totalPages}" var="i" step="1" varStatus="s">
            <c:if test="${i==sessionScope.UsersToShow.currentPage}">
                <li class="active"><a
                        href="${pageContext.request.contextPath}/showUsersWithPagingServlet?currentPage=${i}&itemsPerPage=5&id=${sessionScope.condition.id[0]}
&username=${sessionScope.condition.username[0]}&password=${sessionScope.condition.password[0]}&gender=${sessionScope.condition.gender[0]}">${i}</a>
                </li>
            </c:if>
            <c:if test="${i!=sessionScope.UsersToShow.currentPage}">
                <li>
                    <a href="${pageContext.request.contextPath}/showUsersWithPagingServlet?currentPage=${i}&itemsPerPage=5&id=${sessionScope.condition.id[0]}
&username=${sessionScope.condition.username[0]}&password=${sessionScope.condition.password[0]}&gender=${sessionScope.condition.gender[0]}">${i}</a>
                </li>
            </c:if>
        </c:forEach>

        <c:if test="${sessionScope.UsersToShow.currentPage==sessionScope.UsersToShow.totalPages}">
            <li class="disabled">
                <a href="#" aria-label="Previous">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.UsersToShow.currentPage!=sessionScope.UsersToShow.totalPages}">
            <li>
                <a href="${pageContext.request.contextPath}/showUsersWithPagingServlet?currentPage=${sessionScope.UsersToShow.currentPage+1}&itemsPerPage=5&id=${sessionScope.condition.id[0]}
&username=${sessionScope.condition.username[0]}&password=${sessionScope.condition.password[0]}&gender=${sessionScope.condition.gender[0]}"
                   aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>


    </ul>
</nav>
<h2 class="paging">共${sessionScope.UsersToShow.totalPages}页，${sessionScope.UsersToShow.totalItems}条记录</h2>

<br>
<br>

<script>
    function deleteUser(id) {
        if (id ===${sessionScope.loginUser.id}) {
            alert("无法删除自己");
        } else {
            if (confirm("您确定要删除吗？")) {
                location.href = "${pageContext.request.contextPath}/deleteServlet?delete_id=" + id + "&currentPage=${sessionScope.UsersToShow.currentPage}";
            }
        }
    }

    function updateUser(id, username, password, gender) {
        location.href = "${pageContext.request.contextPath}/rewriteServlet?id=" + id + "&username=" + username + "&password=" + password + "&gender=" + gender;

    }
</script>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="js/jquery-3.4.1.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ"
        crossorigin="anonymous"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
        crossorigin="anonymous"></script>
</body>
</html>