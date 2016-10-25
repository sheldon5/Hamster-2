<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${article.title}</title>
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
        <a class="title-text nav-title" style="color: #e38d13" href="${contextPath}/public/contact.jsp">Coselding</a>
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
                    <li><a href="${contextPath}/public/contact.jsp">关于我</a></li>
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
                <a href="${contextPath}/public/contact.jsp" target="_blank">林宇强</a>
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
            <div class="panel panel-default">
                <div class="panel-body">
                    <h2 class="panel-title text-left">
                        <a href="${contextPath}/list?cid=${article.cid}" title="${article.cname}">
                        ${article.cname}
                        </a>
                        &gt;&gt;
                        ${article.title}
                    </h2>
                </div>
                <div class="panel-body">
                    <h2 class="panel-title text-center">${article.title}</h2>
                </div>

                <div class="panel-body">
                    <div class="text-center">
                        <a href="${contextPath}/list?cid=${article.cid}" title="查看该标签文章">
                            <span class="glyphicon glyphicon-tags"></span> ${article.cname}
                        </a>
                        &nbsp;&nbsp;
                        <a href="#">
                            <span class="glyphicon glyphicon-user"></span> ${article.author}
                        </a>
                        &nbsp;&nbsp;
                        <span class="glyphicon glyphicon-time"></span> ${article.time?string("yyyy-MM-dd HH:mm")}
                    </div>
                </div>

                <div class="panel-body">
                ${typeString}
                ${article.content}
                ${typeString}
                </div>

                <div class="panel-body">
                    <div>
                        <a id="looked" href="#"><span class="glyphicon glyphicon-eye-open"></span> ${article.looked} 已阅</a>
                        &nbsp;&nbsp;
                        <a id="likes" href="javascript:like('${contextPath}/like?artid=${article.artid}')">
                            <span class="glyphicon glyphicon-heart-empty"></span> ${article.likes} 喜爱
                        </a>
                        &nbsp;&nbsp;
                        <a class="comment-btn" href="javascript:onComment('${contextPath}/comment','${article.title}','${article.artid}')"><span class="glyphicon glyphicon-comment"></span> 给我留言</a>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="text-left">
                        <a href="${contextPath}${lastArticle.staticURL}/" title="上一篇">
                            &laquo;${lastArticle.title}
                        </a>
                    </div>
                    <div class="text-right">
                        <a href="${contextPath}${nextArticle.staticURL}/" title="下一篇">
                            ${nextArticle.title}&raquo;
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-3 column">

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title"><span class="glyphicon glyphicon-bullhorn"></span>&nbsp; 最近更新</h3>
                </div>

                <ul id="last3" class="list-group">
                <#list lastArticlesList as la>
                    <li class="list-group-item">
                        <div class="list-group-item-heading text-left">
                            <h5><a href="${contextPath}${la.staticURL}/">${la.title}</a></h5>
                        </div>
                        <div class="list-group-item-text text-right">
                            <em>${la.time?string("yyyy-MM-dd HH:mm")}</em>
                        </div>
                    </li>
                </#list>

                    <li class="list-group-item text-center">
                        <a href="${contextPath}/list">more</a>
                    </li>
                </ul>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h5 class="text-left"><span class="glyphicon glyphicon-search"></span> &nbsp;搜索文章</h5>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="${contextPath}/search">
                        <div class="form-group">
                            <input class="form-control" type="text" name="key" value="输入关键字搜索博客..." onFocus="this.value=''"
                                   onBlur="this.value='输入关键字搜索博客...'"/>
                        </div>
                    </form>
                </div>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title"><span class="glyphicon glyphicon-tags"></span> &nbsp;标签</h3>
                </div>

                <ul id="categories" class="list-group">
                <#list categoryList as cl>
                    <li class="list-group-item"><a href="${contextPath}/list?cid=${cl.cid}">${cl.cname}(${cl.count})</a></li>
                </#list>
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
</body>
<script type="text/javascript" src="${contextPath}/js/scripts.js"></script>
<script type="text/javascript">
    $(function(){//网页加载完成执行
        refreshLookedAndLikes('${contextPath}/refresh?artid=${article.artid}');
        refreshLast3Articles('${contextPath}/last3','${contextPath}');
        refreshCategories('${contextPath}/categories','${contextPath}');
    });
</script>
</html>