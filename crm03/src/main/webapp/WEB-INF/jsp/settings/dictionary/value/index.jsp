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
				var flag = $("#qx").prop("checked");
				$("input[name=xz]").prop("checked",flag);
			})

			$("input[name=xz]").click(function () {
				$("#qx").prop("checked",$("input[name=xz]:checked").length==$("input[name=xz]").length)
			})

			//to创建页面
			$("#toSaveTypeBtn").click(function () {

				window.location.href="settings/dictionary/value/toSaveValue.do";
			})

			//去编辑页面
			$("#toUpdateTypeBtn").click(function () {
				var $xz = $("input[name=xz]:checked");
				if($xz.length==0){
					alert("请选择要编辑的选项")
				}else if($xz.length>1){
					alert("只能编辑一个哦")
				}else{

					var value = $("input[name=xz]:checked").closest("tr").find("td:eq(2)").html();
					var typeCode = $("input[name=xz]:checked").closest("tr").find("td:eq(5)").html();

					var param="value="+value+"&typeCode="+typeCode;
					window.location.href="settings/dictionary/value/toUpdateValue.do?"+param;
				}

			})

			//删除
			$("#deleteTypeBtn").click(function () {


				var $ids = $("input[name=xz]:checked");

				if($ids.length==0){
					alert("请选择要删除的选项");
				}else{

					if(confirm("确定要进行删除吗？")){
						var param="";
						for(var i=0;i<$ids.length;i++){
							param+=$($ids[i]).val();

							if(i<$ids.length-1){
								param+="&";
							}
						}

						window.location.href="settings/dictionary/value/deleteValue.do?ids="+param;

					}



				}
			})

		})
	</script>


</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典值列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" id="toSaveTypeBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button type="button" class="btn btn-default" id="toUpdateTypeBtn"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button type="button" class="btn btn-danger" id="deleteTypeBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="qx" name="qx"/></td>
					<td>序号</td>
					<td>字典值</td>
					<td>文本</td>
					<td>排序号</td>
					<td>字典类型编码</td>
				</tr>
			</thead>
			<tbody>

			<c:forEach items="${dicValues}" var="dv" varStatus="vs">
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="xz" name="xz" value="${dv.id}"/></td>
					<td>${vs.count}</td>
					<td>${dv.value}</td>
					<td>${dv.text}</td>
					<td>${dv.orderNo}</td>
					<td>${dv.typeCode}</td>
				</tr>

			</c:forEach>


				<%--<tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td>m</td>
					<td>男</td>
					<td>1</td>
					<td>sex</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>2</td>
					<td>f</td>
					<td>女</td>
					<td>2</td>
					<td>sex</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>3</td>
					<td>1</td>
					<td>一级部门</td>
					<td>1</td>
					<td>orgType</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>4</td>
					<td>2</td>
					<td>二级部门</td>
					<td>2</td>
					<td>orgType</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>5</td>
					<td>3</td>
					<td>三级部门</td>
					<td>3</td>
					<td>orgType</td>
				</tr>--%>

			</tbody>
		</table>
	</div>

	
</body>
</html>