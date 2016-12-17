<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/10/4 0004
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <!-- 包括所有已编译的插件 -->
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link href="${pageContext.request.contextPath}/admin/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/admin/mheader.jsp"/>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-10 column">
            <div class="container">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title text-center">用户管理</div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>uid</th>
                            <th>用户名</th>
                            <th>注册时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="user">
                            <tr>
                                <td>${user.uid}</td>
                                <td>${fn:escapeXml(user.uname)}</td>
                                <td><fmt:formatDate value="${user.utime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                                <td>
                                    <button class="btn btn-danger" onclick="return deleteUserConfirm('${pageContext.request.contextPath}/manage/user/delete?uid=${user.uid}','${fn:escapeXml(user.uname)}')">删除</button>&nbsp;
                                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/manage/user/update?uid=${user.uid}">修改</a>&nbsp;
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <jsp:include page="/admin/mpage.jsp"/>
            </div>

        </div>
    </div>
</div>

<jsp:include page="/admin/mfooter.jsp"/>
</body>
</html>
