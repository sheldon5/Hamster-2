<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/10/9 0009
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 添加类别模态框 -->
<div class="modal fade" id="addCategoryDialog" tabindex="-1" role="dialog" aria-labelledby="addCategoryLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="addCategoryLabel">添加标签/类别</h4>
            </div>
            <div class="modal-body" id="addCategoryloadBody">
                <div class="row clearfix">
                    <div class="col-md-2 column"></div>
                    <div class="col-md-8 column">
                        <div class="panel">
                            <div class="panel-body">
                                <form id="addCategoryForm" role="form">
                                    <div class="form-group">
                                        <label for="cnameId" class="col-sm-4 control-label">类别名称：</label>
                                        <div class="col-sm-8">
                                            <input type="text" id="cnameId" class="form-control">
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

<!-- 编辑器切换选择模态框-->
<div class="modal fade" id="editorDialog" tabindex="-1" role="dialog" aria-labelledby="editorBody"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="editorLabel">编辑器切换</h4>
            </div>
            <div class="modal-body" id="editorBody">
                <div>
                    切换编辑器可能会使当前页面部分数据丢失，请确保做好内容备份工作！
                </div>
                <div class="row clearfix">
                    <div class="col-md-1 column"></div>
                    <div class="col-md-10 column">
                        <div class="panel">
                            <div class="panel-body">
                                <form id="editorForm" role="form">
                                    <div class="form-group">
                                        <label for="load" class="col-sm-3 control-label">切换类型：</label>
                                        <div class="col-sm-9">
                                            <select id="load" name="load" class="form-control">
                                                <option value="0" selected>两个编辑器各自保存自己的文本</option>
                                                <option value="1">将当前编辑器的文本复制转换到目标编辑器</option>
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
                <button type="button" class="btn btn-primary" id="btn_editor_ok">确定</button>
            </div>
        </div>
    </div>
</div>

<!-- 图片上传模态框 -->
<div class="modal fade" id="customerUploadDialog" tabindex="-1" role="dialog" aria-labelledby="customerUploadLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="customerUploadLabel">自定义图片上传</h4>
            </div>
            <div class="modal-body" id="customerUploadBody">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!--上传表单界面-->
<div class="modal fade" id="uploadBefore" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="row clearfix">
        <div class="col-md-1 column"></div>
        <div class="col-md-10 column">
            <div class="panel">
                <div class="panel-body">
                    <form id="customerUploadForm" role="form" enctype="multipart/form-data"
                          method="post">
                        <div class="form-group">
                            <div class="col-sm-8">
                                <input type="file" name="upload" id="upload" class="form-control">
                            </div>
                            <div class="col-sm-4">
                                <input class="btn btn-primary" type="button"
                                       onclick="return uploadCustomerImage('${pageContext.request.contextPath}/manage/upload/customer')"
                                       value="上传">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-1 column"></div>
    </div>
</div>
<!--上传完成之后的信息页面-->
<div class="modal fade" id="uploadSuccess" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="panel">
        <div class="panel-body">
            <div class="text-center">
                <img id="uploadSuccessImage" style="margin-right: auto;margin-left: auto;width: 40%;height: auto;">
            </div>
            <p> &nbsp;</p>
            <div>
                <div class="col-sm-1">&nbsp;</div>
                <div class="col-sm-7">
                    <input class="form-control" type="text" id="uploadSuccessAddr">&nbsp;&nbsp;
                </div>
                <div class="col-sm-4">
                    <button id="copyBtn" class="btn btn-info" data-clipboard-target="#uploadSuccessAddr">
                        复制地址
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
