<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>欢迎来到Coselding博客 -- 静态</title>
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

    <link href="${contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
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
        <img src="${contextPath}/logo.jpg" style="border-radius:25px;" width="50px" height="50px">&nbsp;&nbsp;
        <a class="title-text nav-title" style="color: #FFFFFF" href="${contextPath}/public/contact.html">Coselding</a>
    </div>
    <div class="collapse navbar-collapse" id="example-navbar-collapse">
        <ul class="nav navbar-nav navbar-right" id="menu-click">
            <li class="active"><a href="/Coselding-old/">旧版入口>></a></li>
            <li><a href="${contextPath}/list">所有文章</a></li>
            <li><a href="https://github.com/Coselding">Github</a></li>
            <li><a href="http://blog.csdn.net/u014394255">CSDN博客</a></li>
            <li><a href="${contextPath}/comment/">留言板</a></li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    Menu<b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="/s/">短网址</a></li>
                    <li><a href="/WordUpload/">作业提交系统</a></li>
                    <li class="divider"></li>
                    <li><a href="${contextPath}/public/contact.html">关于我</a></li>
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
                <a href="${contextPath}/public/contact.html" target="_blank">林宇强</a>
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

<div class="container">
    <div class="row clearfix">
        <div class="col-md-9 column">
        <#list topArticles as art>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a href="${contextPath}${art.staticURL}">
                        <#if art.top==1>
                            <font color="#c9302c"><span class="glyphicon glyphicon-star"></span> [顶]</font>
                        <#else>
                        </#if>
                        ${art.title}
                        </a>
                    </h3>
                </div>
                <a href="${contextPath}${art.staticURL}">
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
                                <a href="${contextPath}/list?cid=${art.cid}"><span class="glyphicon glyphicon-tags"></span> ${art.cname}</a>&nbsp;&nbsp;
                                <a href="#"><span class="glyphicon glyphicon-user"></span> ${art.author}</a>&nbsp;&nbsp;
                                <span class="glyphicon glyphicon-time"></span> ${art.time?string("yyyy-MM-dd HH:mm")}&nbsp;&nbsp;
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="text-right">
                                <a id="likes-${art.artid}" href="javascript:like1('${contextPath}/like?artid=${art.artid}','${art.artid}')" class="likeThis">
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
        </#list>
            <div class="panel panel-default">
                <a href="${contextPath}/list">
                    <div class="panel-heading">
                        <div class="panel-title text-center">
                            <h4>查看更多</h4>
                        </div>
                    </div>
                </a>
            </div>
        </div>

        <div class="col-md-3 column">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title"><span class="glyphicon glyphicon-bullhorn"></span>&nbsp; 最近更新</h3>
                </div>

                <ul id="last3" class="list-group">
                <#list lastArticlesList as art>
                    <li class="list-group-item">
                        <div class="list-group-item-heading text-left">
                            <h5><a href="${contextPath}${art.staticURL}">${art.title}</a></h5>
                        </div>
                        <div class="list-group-item-text text-right">
                            <em>${art.time?string("yyyy-MM-dd HH:mm")}</em>
                        </div>
                    </li>
                </#list>

                    <li class="list-group-item text-center">
                        <a href="${contextPath}/list">more</a>
                    </li>
                </ul>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h5 class="text-left"><span class="glyphicon glyphicon-search"></span> &nbsp;搜索文章</h5>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="${contextPath}/search">
                        <div class="form-group">
                            <input class="form-control" type="text" name="key" value="输入关键字搜索博客..."
                                   onFocus="this.value=''"
                                   onBlur="this.value='输入关键字搜索博客...'"/>
                        </div>
                    </form>
                </div>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title"><span class="glyphicon glyphicon-tags"></span> &nbsp;标签分类</h3>
                </div>
                <ul id="categories" class="list-group">
                <#list categories as cate>
                    <li class="list-group-item">
                        <a style="text-align: left" href="${contextPath}/list?cid=${cate.cid}">${cate.cname}(${cate.count})</a>
                    </li>
                </#list>
                </ul>
            </div>

            <div class="panel panel-primary">
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

<footer class="panel-footer">
    <div class="container">
        <div class="row clearfix text-center">
            <a href="/s/">短网址</a>&nbsp;&nbsp;
            <a href="/WorkUpload/">作业提交系统</a>&nbsp;&nbsp;
            <!--<a href="/uShare/">uShare</a>&nbsp;&nbsp;-->
            <!--<a href="/smartor/">Smartor</a>&nbsp;&nbsp;-->
            <!--<a href="/PlaneWar/">飞机大战</a>-->
        </div>
        <div class="row clearfix text-center">
            <div class="copyright">
                Copyright &copy;2016.Coselding &nbsp; All rights reserved.<a href="http://www.miitbeian.gov.cn">鲁ICP备15036981号-2</a>
            </div>

            <div class="netsupervisor-div">
                <a class="netsupervisor-a" target="_blank"
                   href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=37021002000115">
                    <img src="${contextPath}/images/beian.png" class="netsupervisor-image"/>
                    <p class="netsupervisor-text">鲁公网安备
                        37021002000115号</p>
                </a>
            </div>
        </div>
    </div>
</footer>
<script type="text/javascript" src="${contextPath}/js/scripts.js"></script>
</body>
<#--<script type="text/javascript">-->
    <#--$(function () {-->
        <#--//刷新最新文章-->
        <#--refreshLast3Articles('${contextPath}/last3','${contextPath}');-->
        <#--//刷新标签列表-->
        <#--refreshCategories('${contextPath}/categories','${contextPath}');-->
    <#--});-->
<#--</script>-->
</html>