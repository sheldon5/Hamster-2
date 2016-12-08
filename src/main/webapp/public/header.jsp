<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/3/16 0016
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <a class="title-text nav-title" style="color: #e38d13" href="${pageContext.request.contextPath}/public/contact.html">Coselding</a>
  </div>
  <div class="collapse navbar-collapse" id="example-navbar-collapse">
    <ul class="nav navbar-nav navbar-right" id="menu-click">
      <li class="active"><a href="/Coselding-old/">旧版入口>></a></li>
      <li><a href="${pageContext.request.contextPath}/list">所有文章</a></li>
      <li><a href="https://github.com/Coselding">Github</a></li>
      <li><a href="http://blog.csdn.net/u014394255">CSDN博客</a></li>
      <li><a href="${pageContext.request.contextPath}/comment/">留言板</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          Menu<b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
          <li><a href="/s/">短网址</a></li>
          <li><a href="/WordUpload/">作业提交系统</a></li>
          <li class="divider"></li>
          <li><a href="${pageContext.request.contextPath}/public/contact.html">关于我</a></li>
        </ul>
      </li>
    </ul>
  </div>
</nav>

<div class="container">
  <div class="container-fluid">
    <h1 class="collection-header" id="sub-title"><span>非淡泊无以明志，非宁静无以致远。</span></h1>
    <div class="collection-info">
            <span class="meta-info mobile-hidden">
                <span class="glyphicon glyphicon-map-marker"></span>
                福建漳州
            </span>&nbsp;&nbsp;
            <span class="meta-info">
                <span class="glyphicon glyphicon-tag"></span>
                Java Developer
            </span>&nbsp;&nbsp;
            <span class="meta-info">
                <span class="glyphicon glyphicon-user"></span>
                <a href="${pageContext.request.contextPath}/public/contact.html" target="_blank">林宇强</a>
            </span>&nbsp;&nbsp;
            <span class="meta-info">
                <span class="glyphicon glyphicon-link"></span>
                <a href="https://github.com/Coselding" target="_blank">Github</a>
            </span>
    </div>
  </div>
</div>


<div class="container">
  <div class="clearfix">
    <div class="col-md-8 column">
      <h3 class="text-left text-primary"></h3>
    </div>
  </div>
</div>