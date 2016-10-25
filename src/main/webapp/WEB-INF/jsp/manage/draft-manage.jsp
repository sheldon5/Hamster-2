<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/10/4 0004
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>草稿管理</title>
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
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>标题</th>
                            <th>时间</th>
                            <th>类型</th>
                            <th>是否顶置</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="art">
                        <tr>
                            <td>${art.artid}</td>
                            <td><a style="color:#0000ff" href="${pageContext.request.contextPath}/manage/draft/update?artid=${art.artid}">${fn:escapeXml(art.title)}</a></td>
                            <td>${fn:replace(art.showtime,'T',' ')}</td>
                            <td>${fn:escapeXml(art.cname)}
                            </td>
                            <td>${art.top==0?'未顶置':'顶置'}</td>
                            <td>
                                <button class="btn btn-danger" onclick="return deleteDraftConfirm('${art.title}','${pageContext.request.contextPath}/manage/draft/delete?artid=${art.artid}')">删除</button>&nbsp;
                                <button class="btn btn-info" onclick="return deployDraft2('${art.title}','${pageContext.request.contextPath}/manage/draft/deploy/${art.artid}')">发布</button>&nbsp;
                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/manage/draft/update?artid=${art.artid}">编辑</a>&nbsp;
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
