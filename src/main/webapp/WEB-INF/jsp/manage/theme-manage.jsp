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
    <title>主题管理</title>
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

    <script src="http://cdn.bootcss.com/jquery.form/3.51/jquery.form.js"></script>
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
                        <div class="panel-title text-center">主题管理</div>
                    </div>

                    <div class="panel-body text-center">
                        <button class="btn btn-primary" onclick="return saveTheme()">保存当前主题</button>
                    </div>
                </div>
                <div class="panel panel-default">
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>主题名</th>
                            <th>主题路径</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${themes}" var="th">
                            <tr>
                                <td><a href="javascript:theme('${pageContext.request.contextPath}/manage/theme/change','${th.name}')">${th.name}</a></td>
                                <td>${th.path}</td>
                                <td>
                                    <a class="btn btn-info" href="javascript:theme('${pageContext.request.contextPath}/manage/theme/change','${th.name}')">加载</a>
                                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/manage/theme/delete?name=${th.name}">删除</a>
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

<!-- 添加类别模态框 -->
<div class="modal fade" id="addThemeDialog" tabindex="-1" role="dialog" aria-labelledby="addThemeLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="addThemeLabel">保存主题</h4>
            </div>
            <div class="modal-body" id="addThemeloadBody">
                <div class="row clearfix">
                    <div class="col-md-2 column"></div>
                    <div class="col-md-8 column">
                        <div class="panel">
                            <div class="panel-body">
                                <form id="addThemeForm" role="form" enctype="multipart/form-data"
                                        action="${pageContext.request.contextPath}/manage/theme/add" method="post">
                                    <div class="form-group">
                                        <label for="name" class="col-sm-4 control-label">主题名称：</label>
                                        <div class="col-sm-8">
                                            <input type="text" id="name" name="name" class="form-control">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="logo" class="col-sm-4 control-label">主题图标：</label>
                                        <div class="col-sm-8">
                                            <input type="file" id="logo" name="logo" class="form-control">
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2 column"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="btn_cancel">取消</button>
                <button type="button" class="btn btn-primary" id="btn_ok">确定</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/admin/mfooter.jsp"/>
</body>
</html>
