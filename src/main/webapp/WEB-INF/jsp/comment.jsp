<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/10/4 0004
  Time: 20:29
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
    <title>留言板</title>
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

    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

    <script src="http://cdn.bootcss.com/jquery.form/3.51/jquery.form.js"></script>
</head>
<body>
<jsp:include page="/public/header.jsp"/>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-2 column"></div>
        <div class="col-md-8 column">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title comment-title"><span class="glyphicon glyphicon-comment"></span> 留言板</h3>
                </div>
                <div class="panel-body">
                    <form id="form" class="form-horizontal" role="form" action="${pageContext.request.contextPath}/comment/submit" method="post">
                        <input type="hidden" name="title" value="${fn:escapeXml(title)}">
                        <input type="hidden" name="artid" value="${fn:escapeXml(artid)}">
                        <div class="form-group">
                            <label for="nickname" class="col-sm-2 control-label">您的昵称：</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="gname" id="nickname"
                                       placeholder="请输入昵称..." value="${fn:escapeXml(comment.gname)}">
                                <span>${errors.gname}</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="gemail" class="col-sm-2 control-label">您的email：</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="gemail" name="gemail"
                                       placeholder="请输入email..." value="${fn:escapeXml(comment.gemail)}">
                                <span>${errors.gemail}</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="comcontent" class="col-sm-2 control-label">说几句话吧:</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="comcontent" name="comcontent"
                                          rows="4"><c:if test="${empty(comment.comcontent)}"><c:if test="${!empty(title)}">#${fn:escapeXml(title)}#</c:if></c:if><c:if test="${!empty(comment.comcontent)}">${fn:escapeXml(comment.comcontent)}</c:if></textarea>
                                <span>${errors.comcontent}</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button onclick="return submitComment('${pageContext.request.contextPath}/comment/submit');" class="btn btn-primary">留言</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <c:forEach items="${page.list}" var="com">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title visitor-text">
                            <span class="glyphicon glyphicon-user"></span> ${fn:escapeXml(com.gname)}
                        </h3>
                    </div>
                    <div class="panel-body">
                        <p>${fn:escapeXml(com.comcontent)}</p>
                    </div>
                    <div class="panel-footer">
                        <div class="clearfix">
                            <div class="col-md-8 column text-left">
                                <a href="mailto:${com.gemail}"><span class="glyphicon glyphicon-envelope"></span> ${com.gemail}</a>
                            </div>
                            <div class="col-md-4 column text-right">
                                <span class="glyphicon glyphicon-time"></span> <fmt:formatDate value="${com.comtime}" pattern="yyyy-MM-dd hh:mm"></fmt:formatDate>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <jsp:include page="/public/page.jsp"/>
        </div>
        <div class="col-md-2 column"></div>
    </div>
</div>

<jsp:include page="/public/footer.jsp"/>
</body>
</html>
