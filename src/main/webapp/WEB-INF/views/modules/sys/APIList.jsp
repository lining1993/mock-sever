<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>API列表</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <%@ include file="/WEB-INF/views/include/treetable.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#treeTable").treetable({expandable: true }).show();
            $('#treeTable').treetable('expandAll');
        });
    </script>
    <style type="text/css">
        .navbar-default .navbar-nav>li>a{
            color: white;
        }
        .navbar-default .navbar-nav>li>a:hover{
            color: white;
            text-decoration: underline;
        }
        .navbar-default .navbar-nav>li.active>a{
            color: black;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-default" role="navigation" style="background-color: #5D478B;color: white;">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span></button>
        <a class="navbar-brand" style="color: white;" href="${ctx}/">Mock-API</a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li class="active">
                <a href="${ctx}/api/list">API列表</a>
            </li>
            <li>
                <a href="${ctx}/api/add/form">API添加</a>
            </li>
            <li>
                <a href="${ctx}/api/respDataForm">配置ResponseData</a>
            </li>
            <li>
                <a href="#" class="api_refresh">API刷新</a>
            </li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="${ctx}/">首页</a>
            </li>
        </ul>
    </div>
</nav>
<div class="box box-solid">
    <div class="box-body">
        <form id="listForm" method="post">
            <table id="treeTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th style="width: 20%;">名称</th>
                    <th>中文名称</th>
                    <th>操作</th>
                    <th><a href="#" class="glyphicon glyphicon-plus" onclick="$('#treeTable').treetable('expandAll'); return false;">&nbsp;</a>
                        <a href="#" class="glyphicon glyphicon-minus" onclick="$('#treeTable').treetable('collapseAll'); return false;">&nbsp;</a></th>
                </tr>
                </thead>
                <tbody><c:forEach items="${apiList}" var="mockAPI">
                    <tr data-tt-id="${mockAPI.id}" data-tt-parent-id="${mockAPI.parentId}">
                        <td nowrap>${mockAPI.name}</td>
                        <td nowrap>${mockAPI.subject.cnName}</td>
                        <td nowrap colspan="2">
                            <%--系统--%>
                            <c:if test="${mockAPI.id < 1000}">
                                <a href="${ctx}/api/add/form?sysId=${mockAPI.id}">添加服务</a>
                            </c:if>
                            <%--服务--%>
                            <c:if test="${mockAPI.id >= 1000 and mockAPI.id < 10000}">
                                <a href="${ctx}/api/add/form?servId=${mockAPI.id}">添加操作</a>
                            </c:if>
                            <%--操作--%>
                            <c:if test="${mockAPI.id >= 10000}">
                                <a href="${ctx}/api/add/form?operId=${mockAPI.id}">查看操作</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach></tbody>
            </table>
        </form>

    </div>
</div>
</body>
</html>
