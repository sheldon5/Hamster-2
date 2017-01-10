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
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>关于我</title>
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
        <div class="col-md-1 column"></div>
        <div class="col-md-10 column">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="text-center">About Me</h3>
                </div>
                <div class="panel-body">
                    <p>
                    <h2>林宇强（Coselding）</h2></p>
                    <p><br/></p>
                    <p><h4>一个热爱编程，对技术无法抗拒的Java Developer~</h4></p>
                    <p><br/></p>
                    <p>
                    <h2>研究方向：</h2></p>
                    <p><br/></p>
                    <p><h4>Java、JavaEE、Hadoop大数据相关、Android</h4></p>
                    <p><br/></p>
                    <p>
                    <h2>联系</h2></p>
                    <p><br/></p>
                    <ul>
                        <li><h4>Github：<a style="color: #4183C4;" href="https://github.com/Coselding" title="Github">Coselding</a>
                        </h4></li>
                        <li><h4>CSDN：<a style="color: #4183C4;" href="http://blog.csdn.net/u014394255" title="CSDN">Coselding</a>
                        </h4></li>
                        <li><h4>微博：<a style="color: #4183C4;" href="http://weibo.com/u/2671168890"
                                      title="微博">Coselding</a></h4></li>
                        <li><h4>Email：<a style="color: #4183C4;" href="mailto:1098129797@qq.com" title="邮件">1098129797@qq.com</a>
                        </h4></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-1 column"></div>
    </div>
</div>
<jsp:include page="/public/footer.jsp"/>
</body>
</html>
