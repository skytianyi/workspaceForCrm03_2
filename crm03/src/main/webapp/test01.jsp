<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>title</title>
</head>
<body>

        $.ajax({

            url : "",
            data : {


            },
            type : "",
            dataType : "json",
            success : function (data) {


            }

        })

        String createBy = ((User)session.getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();

        pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
        ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


</body>
</html>
