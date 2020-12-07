<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

	<script type="text/javascript">
		$(function () {

			//全选、全不选
			$("#qx").click(function () {
				var flag = $("input[name=qx]").prop("checked");
				$("input[name=xz]").prop("checked",flag);
			})

			//反选
			$("input[name=xz]").click(function () {
				$("input[name=qx]").prop("checked",$("input[name=xz]:checked").length==$("input[name=xz]").length);
			})

			//编辑返回数据
			$("#toUpdateBtn").click(function () {
				var $xz = $("input[name=xz]:checked");
				if($xz.length==0){
					alert("请选择要选择的选项")
				}else if($xz.length>1){
					alert("只能选择一个哦")
				}else{
					var code = $xz.val();

					window.location.href="settings/dictionary/toTypeUpdate.do?code="+code;

				}
			})

			//删除
			$("#deleteTypeBtn").click(function () {
				var $xz = $("input[name=xz]:checked");
				if($xz.length==0){
					alert("请选择要删除的选项")
					return;
				}
				if(confirm("确定要删除吗？")){

					var param="";
					for(var i=0;i<$xz.length;i++){
						var code = $($xz[i]).val();
						param+="codes="+code;

						if(i<$xz.length-1){
							param+="&";
						}
					}

					window.location.href="settings/dictionary/deleteType.do?"+param;

				}

			})


		})
	</script>

</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典类型列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/toTypeSave.do'"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button type="button" class="btn btn-default" id="toUpdateBtn"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button type="button" class="btn btn-danger" id="deleteTypeBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="qx" name="qx" /></td>
					<td>序号</td>
					<td>编码</td>
					<td>名称</td>
					<td>描述</td>
				</tr>
			</thead>
			<tbody>
				<%--<tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td>sex</td>
					<td>性别</td>
					<td>性别包括男和女</td>
				</tr>--%>

			<c:forEach items="${dtList}" var="dt" varStatus="vs">

				<tr class="active">
					<td><input type="checkbox" name="xz" value="${dt.code}"/></td>
					<td>${vs.count}</td>
					<td>${dt.code}</td>
					<td>${dt.name}</td>
					<td>${dt.description}</td>
				</tr>
			</c:forEach>


			</tbody>
		</table>
	</div>
	
</body>
</html>