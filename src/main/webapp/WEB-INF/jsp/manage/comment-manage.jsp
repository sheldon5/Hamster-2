<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/10/4 0004
  Time: 16:06
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
    <title>${pageTitle}</title>
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
                        <div class="panel-title text-center">${pageTitle}</div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <input type="hidden" id="gid" name="gid" value="${gid}">
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>留言id</th>
                            <th>客户id</th>
                            <th>留言内容</th>
                            <th>文章id</th>
                            <th>审核情况</th>
                            <th>留言时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="comment">
                            <tr>
                                <td>${comment.comid}</td>
                                <td>
                                    <a style="color:#0000ff" href="${pageContext.request.contextPath}/manage/guest/comments?gid=${comment.gid}">${comment.gid}</a>
                                </td>
                                <td><a href="javascript:getFullComcontent('${comment.comid}','${pageContext.request.contextPath}')">${fn:escapeXml(comment.comcontent)}</a></td>
                                <td>
                                    <a style="color:#0000ff" href="${pageContext.request.contextPath}/manage/article/${comment.artid}">${comment.artid}</a>
                                </td>
                                <td id="comid-${comment.comid}">
                                    <c:choose>
                                        <c:when test="${comment.pass==0}">
                                            <button class="btn btn-primary" onclick="return passComment('${pageContext.request.contextPath}/manage/comment/pass','${comment.comid}')">
                                                未审核
                                            </button>
                                        </c:when>
                                        <c:when test="${comment.pass==1}">
                                            通过
                                        </c:when>
                                        <c:when test="${comment.pass==2}">
                                            未通过
                                        </c:when>
                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><fmt:formatDate value="${comment.comtime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                                <td>
                                    <button class="btn btn-danger" onclick="return deleteCommentConfirm('${pageContext.request.contextPath}/manage/comment/delete?comid=${comment.comid}','${comment.comid}')">删除</button>&nbsp;
                                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/manage/comment/update?comid=${comment.comid}">修改</a>&nbsp;
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

<!-- 编辑器切换选择模态框-->
<div class="modal fade" id="passDialog" tabindex="-1" role="dialog" aria-labelledby="passBody"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="passLabel">留言审核</h4>
            </div>
            <div class="modal-body" id="passBody">
                <div>
                    请选择该留言的审核选项:
                </div>
                <div class="row clearfix">
                    <div class="col-md-1 column"></div>
                    <div class="col-md-10 column">
                        <div class="panel">
                            <div class="panel-body">
                                <form id="passForm" role="form">
                                    <div class="form-group">
                                        <div class="col-sm-9">
                                            <select id="passD" name="pass" class="form-control">
                                                <option value="1" selected>通过</option>
                                                <option value="2">未通过</option>
                                            </select>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-1 column"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="btn_pass_ok">确定</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/admin/mfooter.jsp"/>
</body>
</html>
