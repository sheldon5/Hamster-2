<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/3/16 0016
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target="#example-navbar-collapse">
            <span class="sr-only">切换导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <div class="navbar-brand"></div>
        <img src="${pageContext.request.contextPath}/logo.jpg" style="border-radius:25px;" width="50px" height="50px">&nbsp;&nbsp;
        <a class="title-text nav-title" style="color: #e38d13" href="${pageContext.request.contextPath}/manage/">Coselding后台管理</a>
    </div>
    <c:if test="${!empty(user)}">
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav navbar-right" id="menu-click">
                <li><a href="javascript:staticIndexComfirm('${pageContext.request.contextPath}/manage/article/reload/index')">主页静态化</a></li>
                <li><a href="${pageContext.request.contextPath}/manage/theme/">主题切换</a></li>
                <li class="dropdown">
                    <a href="${pageContext.request.contextPath}/manage/article/" class="dropdown-toggle" data-toggle="dropdown">
                        文章管理<b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/manage/article/">文章管理</a></li>
                        <li><a href="${pageContext.request.contextPath}/manage/article/add/">添加文章</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="${pageContext.request.contextPath}/manage/category/" class="dropdown-toggle" data-toggle="dropdown">
                        类别管理<b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/manage/category/">类别管理</a></li>
                        <li><a href="${pageContext.request.contextPath}/manage/category/add/">添加类别</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="${pageContext.request.contextPath}/manage/draft/" class="dropdown-toggle" data-toggle="dropdown">
                        草稿管理<b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/manage/draft/">草稿管理</a></li>
                        <li><a href="${pageContext.request.contextPath}/manage/draft/add/">添加草稿</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="${pageContext.request.contextPath}/manage/user/" class="dropdown-toggle" data-toggle="dropdown">
                        用户管理<b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/manage/user/">用户管理</a></li>
                        <li><a href="${pageContext.request.contextPath}/manage/user/add/">添加用户</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="${pageContext.request.contextPath}/manage/guest/" class="dropdown-toggle" data-toggle="dropdown">
                        访客管理<b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/manage/guest/">访客管理</a></li>
                        <li><a href="${pageContext.request.contextPath}/manage/guest/add/">添加访客</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="${pageContext.request.contextPath}/manage/comment/" class="dropdown-toggle" data-toggle="dropdown">
                        留言管理<b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/manage/comment/">留言管理</a></li>
                        <li><a href="${pageContext.request.contextPath}/manage/comment/wait">待审核留言</a></li>
                        <li><a href="${pageContext.request.contextPath}/manage/comment/add/">添加留言</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </c:if>
</nav>