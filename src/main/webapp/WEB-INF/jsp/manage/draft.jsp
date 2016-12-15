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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/md/css/editormd.css"/>
    <script src="http://cdn.bootcss.com/jquery.form/3.51/jquery.form.js"></script>
    <script src="${pageContext.request.contextPath}/admin/js/clipboard.min.js"></script>
</head>
<body>
<jsp:include page="/admin/mheader.jsp"/>
<div class="container">
    <div class="row clearfix">
        <%--<div class="col-md-1 column"></div>--%>
        <div class="col-md-12 column">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title text-center">${fn:escapeXml(pageTitle)}</h3>
                </div>
                <div class="panel-body">
                    <form id="form" role="form"
                          action="${pageContext.request.contextPath}/manage/draft/${method}-go/" method="post" onsubmit="return articleSubmitCheck()">
                        <input id="artid" type="hidden" name="artid" value="${artid}">
                        <input id="editor" type="hidden" name="editor" value="${draft.editor}">
                        <input id="method" type="hidden" name="method" value="${method}">
                        <input id="deploy" type="hidden" name="deploy" value="1">
                        <input id="loadType" type="hidden" name="loadType" value="0">
                        <div class="form-group">
                            <label for="title">文章标题：</label>
                            <input type="text" class="form-control" name="title" id="title"
                                   value="${fn:escapeXml(draft.title)}">
                            <span>${errors.title}</span>
                        </div>
                        <div class="form-group">
                            <label for="urlTitle">文章URL标题：</label>
                            <input type="text" class="form-control" name="urlTitle" id="urlTitle"
                                   value="${fn:escapeXml(draft.urlTitle)}">
                            <span>${errors.urlTitle}</span>
                        </div>
                        <div class="form-group">
                            <label for="cid">类别：</label>
                            <select class="form-control" id="cid" name="cid">
                                <c:forEach items="${categories}" var="cate">
                                    <c:if test="${!empty(cidDefault)}">
                                        <option value="${cate.cid}" ${draft.cid==cate.cid?'selected':''}>${fn:escapeXml(cate.cname)}
                                        </option>
                                    </c:if>
                                    <c:if test="${empty(cidDefault)}">
                                        <option id="cid-default"
                                                value="${cate.cid}" ${draft.cid==cate.cid?'selected':''}>${fn:escapeXml(cate.cname)}
                                        </option>
                                        <c:set var="cidDefault" value="true" scope="request"/>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <span>
                                <button class="btn btn-primary"
                                        onclick="return addCategory('${pageContext.request.contextPath}/manage/category/add-go/ajax')">
                                    添加类别
                                </button>
                            </span>
                        </div>
                        <div class="form-group">
                            <label for="type"></label>
                            <select id="type" class="form-control" name="type">
                                <option value="原创" ${draft.type=='原创'?'selected':''}>原创</option>
                                <option value="转载" ${draft.type=='转载'?'selected':''}>转载</option>
                            </select>
                            <span></span>
                        </div>
                        <div class="form-group">
                            <label for="time">时间：</label>
                            <input type="datetime-local" class="form-control" name="showtime" id="time"
                                   value="${draft.showtime}">
                            <span>${errors.showtime}</span>
                        </div>
                        <div class="form-group">
                            <label for="top">是否顶置：</label>
                            <select id="top" class="form-control" name="top">
                                <option value="0" ${draft.top==0?'selected':''}>不顶置</option>
                                <option value="1" ${draft.top==1?'selected':''}>顶置</option>
                            </select>
                            <span>${errors.top}</span>
                        </div>
                        <c:if test="${method=='update'}">
                            <div class="form-group">
                                <label for="meta">摘要：</label>
                                <textarea class="form-control" name="meta" id="meta" cols="80"
                                          rows="8">${fn:escapeXml(draft.meta)}</textarea>
                                <span>${errors.meta}</span>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="content">文章内容:</label>
                            &nbsp;&nbsp;
                            <c:if test="${draft.editor==1}">
                                <button class="btn btn-primary" id="btn-editor"
                                        onclick="return editorChange('${pageContext.request.contextPath}/manage/draft/editor/','0')">
                                    MarkDown编辑器
                                </button>
                            </c:if>
                            <c:if test="${draft.editor==0}">
                                <button class="btn btn-primary" id="btn-editor"
                                        onclick="return editorChange('${pageContext.request.contextPath}/manage/draft/editor/','1')">
                                    CKEditor编辑器
                                </button>
                            </c:if>
                            &nbsp;&nbsp;
                            <button onclick="return customerImageUpload()" class="btn btn-primary">
                                上传图片
                            </button>
                            &nbsp;&nbsp;
                            <button onclick="return resetFromDatabase('${pageContext.request.contextPath}/manage/draft/reset','${pageContext.request.contextPath}')"
                                    class="btn btn-primary">
                                reset from db
                            </button>
                            &nbsp;&nbsp;
                            <span>${errors.content}<br/></span>
                            <div id="editor-parent">
                                <c:if test="${draft.editor==1}">
                                    <div id="ckeditor">
                                    <textarea class="form-control" name="content"
                                              id="content">${fn:escapeXml(draft.content)}</textarea>
                                    </div>
                                </c:if>
                                <c:if test="${draft.editor==0}">
                                    <div id="markdown">
                                <textarea class="form-control" style="display:none;"
                                          name="md" id="md">${fn:escapeXml(draft.md)}</textarea>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="text-right">
                            <button onclick="return submitArticle()" class="btn btn-info">
                                保存
                            </button>
                            &nbsp;&nbsp;&nbsp;
                            <button class="btn btn-primary" onclick="return deployDraft('${draft.title}')">
                                发布
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%--<div class="col-md-1 column"></div>--%>
    </div>
</div>

<jsp:include page="/admin/modal.jsp"/>
<jsp:include page="/admin/mfooter.jsp"/>
</body>
<c:if test="${draft.editor==1}">
    <script type="text/javascript" src="${pageContext.request.contextPath}/ck/ckeditor.js"></script>
</c:if>
<c:if test="${draft.editor==0}">
    <script type="text/javascript" src="${pageContext.request.contextPath}/md/editormd.js"></script>
</c:if>
<script type="text/javascript">
    //页面初始化
    $(function () {
        <%-- 初始化CKEditor --%>
        <c:if test = "${draft.editor==1}" >
        initCKEditor('${pageContext.request.contextPath}');
        </c:if>

        <%-- 初始化editormd --%>
        <c:if test = "${draft.editor==0}" >
        initEditormd('${pageContext.request.contextPath}');
        </c:if>

        <%-- 初始化剪切板控件 --%>
        new Clipboard('#copyBtn');
    });
</script>
</html>
