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
    <title>类别管理</title>
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
        <div class="col-md-1 column"></div>
        <div class="col-md-10 column">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title text-center">类别管理</h3>
                </div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>cid</th>
                            <th>类别名称</th>
                            <th>创建时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="cate">
                            <tr>
                                <td>${cate.cid}</td>
                                <td><a style="color:#0000ff" href="${pageContext.request.contextPath}/manage/article/list?cid=${cate.cid}">${fn:escapeXml(cate.cname)}</a>
                                </td>
                                <td><fmt:formatDate value="${cate.ctime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                                <td>
                                    <button class="btn btn-danger" onclick="return deleteCategoryConfirm('${pageContext.request.contextPath}/manage/category/delete?cid=${cate.cid}','${fn:escapeXml(cate.cname)}')">删除</button>&nbsp;
                                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/manage/category/update?cid=${cate.cid}">修改</a>&nbsp;
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <jsp:include page="/admin/mpage.jsp"/>
        </div>
        <div class="col-md-1 column"></div>
    </div>
</div>

<jsp:include page="/admin/mfooter.jsp"/>
</body>
</html>
