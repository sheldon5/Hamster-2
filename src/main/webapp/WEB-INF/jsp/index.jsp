<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/10/4 0004
  Time: 20:30
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
    <title>欢迎来到Coselding博客 -- 动态</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="http://bootswatch.com/cerulean/bootstrap.min.css" rel="stylesheet">
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
</head>
<body>
<jsp:include page="/public/header.jsp"/>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-9 column">
        <c:forEach items="${params.topArticles}" var="art">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a href="${pageContext.request.contextPath}${art.staticURL}">
                            <font color="#c9302c">${art.top==1?'<span class="glyphicon glyphicon-star"></span> [顶]':''}</font>
                                ${fn:escapeXml(art.title)}
                        </a>
                    </h3>
                </div>
                <a href="${pageContext.request.contextPath}${art.staticURL}">
                    <div class="panel-body">
                        <p>
                        <p>${art.meta}
                        </p>
                        </p>
                    </div>
                </a>

                <div class="panel-footer">
                    <div class="row clearfix">
                        <div class="col-md-9">
                            <div class="text-left">
                                <a href="#">${art.type}</a>&nbsp;&nbsp;
                                <a href="${pageContext.request.contextPath}/list?cid=${art.cid}"><span class="glyphicon glyphicon-tags"></span> ${fn:escapeXml(art.cname)}</a>&nbsp;&nbsp;
                                <a href="#"><span class="glyphicon glyphicon-user"></span> ${fn:escapeXml(art.author)}</a>&nbsp;&nbsp;
                                <span class="glyphicon glyphicon-time"></span><fmt:formatDate value="${art.time}" pattern="yyyy-MM-dd hh:mm"></fmt:formatDate>&nbsp;&nbsp;
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="text-right">
                                <a id="likes-${art.artid}" href="javascript:like1('${pageContext.request.contextPath}/like?artid=${art.artid}','${art.artid}')" class="likeThis">
                                    <span class="glyphicon glyphicon-heart"></span> ${art.likes}
                                </a>
                                &nbsp;&nbsp;
                                <a id="looked-${art.artid}" href="#">
                                    <span class="glyphicon glyphicon-eye-open"></span> ${art.looked}
                                </a>
                                &nbsp;&nbsp;
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
            <div class="panel panel-default">
                <a href="${pageContext.request.contextPath}/list">
                    <div class="panel-heading">
                        <div class="panel-title text-center">
                            <h4>查看更多</h4>
                        </div>
                    </div>
                </a>
            </div>
        </div>

        <div class="col-md-3 column">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title"><span class="glyphicon glyphicon-bullhorn"></span>&nbsp; 最近更新</h3>
                </div>

                <ul class="list-group">
                <c:forEach items="${params.lastArticlesList}" var="art">
                    <li class="list-group-item">
                        <div class="list-group-item-heading text-left">
                            <h5><a href="${pageContext.request.contextPath}${art.staticURL}">${fn:escapeXml(art.title)}</a></h5>
                        </div>
                        <div class="list-group-item-text text-right">
                            <em><fmt:formatDate value="${art.time}" pattern="yyyy-MM-dd hh:mm"></fmt:formatDate></em>
                        </div>
                    </li>
                </c:forEach>
                    <li class="list-group-item text-center">
                        <a href="${pageContext.request.contextPath}/list">more</a>
                    </li>
                </ul>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h5 class="text-left"><span class="glyphicon glyphicon-search"></span> &nbsp;搜索文章</h5>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="${pageContext.request.contextPath}/search">
                        <div class="form-group">
                            <input class="form-control" type="text" name="key" value="输入关键字搜索博客..."
                                   onFocus="this.value=''"
                                   onBlur="this.value='输入关键字搜索博客...'"/>
                        </div>
                    </form>
                </div>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title"><span class="glyphicon glyphicon-tags"></span> &nbsp;标签分类</h3>
                </div>
                <ul class="list-group">
                <c:forEach items="${params.categories}" var="cate">
                    <li class="list-group-item">
                        <a style="text-align: left" href="${pageContext.request.contextPath}/list?cid=${cate.cid}">${fn:escapeXml(cate.cname)}(${cate.count})</a>
                    </li>
                </c:forEach>
                </ul>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title"><span class="glyphicon glyphicon-link"></span> &nbsp;友情链接</h3>
                </div>
                <ul class="list-group">
                    <li class="list-group-item"><a href="https://www.zning.net.cn">张宁网</a></li>
                    <li class="list-group-item"><a href="https://cloudups.github.io">凌云阁</a></li>
                    <li class="list-group-item"><a href="https://noahzu.github.io">Android资源开发小栈</a></li>
                    <li class="list-group-item"><a href="https://heinika.github.io/">陈利津</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/public/footer.jsp"/>
</body>
</html>
