<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/3/16 0016
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 确认模态框（Modal） -->
<div class="modal fade" id="comfirmDialog" tabindex="-1" role="dialog" aria-labelledby="myComfirmLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="myComfirmLabel">模态框（Modal）标题</h4>
      </div>
      <div class="modal-body" id="myComfirmBody">按下 ESC 按钮退出。</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="btn_cancel">取消</button>
        <button type="button" class="btn btn-primary" id="btn_comfirm_ok">确定</button>
      </div>
    </div>
  </div>
</div>
<!-- /.modal -->
<!-- 消息模态框 -->
<div class="modal fade" id="msgDialog" tabindex="-1" role="dialog" aria-labelledby="myMsgLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="myMsgLabel">模态框（Modal）标题</h4>
      </div>
      <div class="modal-body" id="myMsgBody">按下 ESC 按钮退出。</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
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
          <img src="${pageContext.request.contextPath}/images/beian.png" class="netsupervisor-image"/>
          <p class="netsupervisor-text">鲁公网安备
            37021002000115号</p>
        </a>
      </div>
    </div>
  </div>
</footer>
<!-- End Footer -->
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/scripts.js"></script>
