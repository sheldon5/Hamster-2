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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文章管理</title>
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
                        <div class="panel-title text-center">文章管理</div>
                    </div>
                    <div class="panel-body text-center">
                        <button onclick="return reloadAllArticles('${pageContext.request.contextPath}/manage/article/reload/all')" class="btn btn-primary">重新静态化所有文章</button>
                        &nbsp;&nbsp;
                        <button onclick="return fomatDatabase('${pageContext.request.contextPath}/manage/article/formatdb')" class="btn btn-primary">数据库数据格式化</button>
                    </div>
                </div>
                <div class="panel panel-default">
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>标题</th>
                            <th>作者</th>
                            <th>时间</th>
                            <th>类型</th>
                            <th>阅读数</th>
                            <th>喜爱数</th>
                            <th>是否顶置</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="art">
                        <tr>
                            <td>${art.artid}</td>
                            <td><a style="color:#0000ff" href="${pageContext.request.contextPath}${art.staticURL}">${fn:escapeXml(art.title)}</a></td>
                            <td>${fn:escapeXml(art.author)}</td>
                            <td><fmt:formatDate value="${art.time}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                            <td><a style="color:#0000ff" href="${pageContext.request.contextPath}/manage/article/list?cid=${art.cid}">${fn:escapeXml(art.cname)}</a>
                            </td>
                            <td>${art.looked}</td>
                            <td>${art.likes}</td>
                            <td>${art.top==0?'未顶置':'顶置'}</td>
                            <td>
                                <button class="btn btn-danger" onclick="return deleteArticleConfirm('${art.title}','${pageContext.request.contextPath}/manage/article/delete?artid=${art.artid}')">删除</button>&nbsp;
                                <button class="btn btn-info" onclick="return reloadArticle('${art.title}','${pageContext.request.contextPath}/manage/article/reload/${art.artid}')">静态化</button>&nbsp;
                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/manage/article/update?artid=${art.artid}">修改</a>&nbsp;
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
