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
    <title>${fn:escapeXml(pageTitle)}</title>
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
                    <h3 class="panel-title text-center">${fn:escapeXml(pageTitle)}</h3>
                </div>
                <div class="panel-body">
                    <form id="form" role="form"
                          action="${pageContext.request.contextPath}/manage/comment/${method}-go/" method="post">
                        <input type="hidden" name="comid" value="${comid}">
                        <div class="form-group">
                            <label for="gid">客户id：</label>
                            <input type="text" class="form-control" name="gid" id="gid" value="${comment.gid}">
                            <span>${errors.gid}</span>
                        </div>
                        <div class="form-group">
                            <label for="comcontent">留言内容：</label>
                            <textarea class="form-control" name="comcontent" id="comcontent" rows="4">${fn:escapeXml(comment.comcontent)}</textarea>
                            <span>${errors.comcontent}</span>
                        </div>
                        <div class="form-group">
                            <label for="artid">文章id：</label>
                            <input type="text" class="form-control" name="artid" id="artid" value="${comment.artid}">
                            <span>${errors.artid}</span>
                        </div>
                        <div class="form-group">
                            <label for="comtimeshow">评论时间：</label>
                            <input type="datetime-local" class="form-control" name="comtimeshow" id="comtimeshow" value="${comment.comtimeshow}">
                            <span>${errors.comtimeshow}</span>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-10 col-sm-2">
                                <button class="btn btn-primary" onclick="return submitManageComment()" width="150"
                                       height="75">
                                    修改
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-1 column"></div>
    </div>
</div>

<jsp:include page="/admin/mfooter.jsp"/>
</body>
</html>