<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="pager">
	<li class="previous"><a href="javascript:submit('1')">&larr; 首页</a></li>

	<c:if test="${page.pagenum>1 }">
		<li><a href="javascript:submit('${page.pagenum-1}')">&laquo;</a></li>
	</c:if>

	<c:forEach begin="${page.startpage}" end="${page.endpage}" var="pagenum">
		<li>
			<a href="javascript:submit('${pagenum}')">
			<c:if test="${page.pagenum==pagenum}"><font color="red"></c:if>
				${pagenum }
			<c:if test="${page.pagenum==pagenum}"></font></c:if>
			</a>
		</li>
	</c:forEach>

	<c:if test="${page.pagenum<page.totalpage }">
		<li><a href="javascript:submit('${page.pagenum+1}')">&raquo;</a></li>
	</c:if>

	<li class="next"><a href="javascript:submit('${page.totalpage}')">末页 &rarr;</a></li>
	&nbsp;&nbsp; 共 [ ${page.totalpage } ]页，共[ ${page.totalrecord } ]条记录
</ul>

<script type="text/javascript">
	function submit(pagenum){
		var form = document.createElement("form");
		form.action = '${page.url}';
		form.method = "post";
		form.style.display = "none";

		var cidEle = document.createElement("input");
		cidEle.name = "cid";
		cidEle.value = '${cid}';
		form.appendChild(cidEle);

		var gidEle = document.createElement("input");
		gidEle.name = "gid";
		gidEle.value = '${gid}';
		form.appendChild(gidEle);

		var keyEle = document.createElement("input");
		keyEle.name = "key";
		keyEle.value = '${key}';
		form.appendChild(keyEle);

		var pagenumEle = document.createElement("input");
		pagenumEle.name = "pagenum";
		pagenumEle.value = pagenum;
		form.appendChild(pagenumEle);

		document.body.appendChild(form);
		form.submit();
	}
</script>
