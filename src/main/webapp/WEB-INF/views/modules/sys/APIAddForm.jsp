<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>配置服务</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <%--jsoneditor--%>
    <script src="${ctxStatic}/jsoneditor/jsoneditor.js" type="text/javascript"></script>
    <link href="${ctxStatic}/jsoneditor/jsoneditor.css" rel="stylesheet"/>
    <script type="text/javascript">
        $(function () {

            $("#submit").on('click',function () {
                var systemName = $('#systemName').val().trim();
                var serverName = $('#serverName').val().trim();
                var operationName = $('#operationName').val().trim();
                var responseDataType = $("input[name='respDataType']:checked").val();
                var defaultResponseData = responseDataType == 'JSON' ? editor.getText() : $('#respData4String').val();
                if(!systemName || !serverName || !operationName
                        || systemName.length<0|| serverName.length<0
                        || operationName.length<0){
                    showInfo("系统名称，服务名称和操作名称均不能为空！","添加API",5000);
                    return;
                }

                $.ajax({
                    url: '${ctx}/api/add',
                    type: 'post',
                    dataType: 'json',
                    data:{
                        "systemName":systemName,
                        "serverName":serverName,
                        "operationName":operationName,
                        "responseDataType":responseDataType,
                        "defaultResponseData":defaultResponseData
                    },
                    success: function (data) {
                        if (data && data.success && data.data) {
                            var url = data.data;
                            showInfo("配置成功！<br/>API路径：<br/>"+url,"添加API",0);
                        }
                    }
                });
            });

            var boxSuffix = "_Box";
            $("div[data-content=box]").hide();
            var boxId = $("input[name='respDataType']:checked").val()+boxSuffix;
            $("#"+boxId).show();

            $("input[name=respDataType]").on('click', function () {
                $("div[data-content=box]").hide();
                var boxId = $(this).val()+boxSuffix;
                $("#"+boxId).show();
            });
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
            <li>
                <a href="${ctx}/api/list">API列表</a>
            </li>
            <li class="active">
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
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label for="systemName" class="col-sm-2 control-label">系统</label>
                <div class="col-sm-5">
                    <input type="text" id="systemName" class="form-control" value="${sysName}"
                           <c:if test="${sysName != null and sysName != ''}">readonly="readonly"</c:if>/>
                </div>
            </div>

            <div class="form-group">
                <label for="serverName" class="col-sm-2 control-label">服务</label>
                <div class="col-sm-5">
                    <input type="text" id="serverName" class="form-control" value="${servName}"
                           <c:if test="${servName != null and servName != ''}">readonly="readonly"</c:if>/>
                </div>
            </div>

            <div class="form-group">
                <label for="operationName" class="col-sm-2 control-label">操作</label>
                <div class="col-sm-5">
                    <input type="text" id="operationName" class="form-control" value="${operName}"
                           <c:if test="${operName != null and operName != ''}">readonly="readonly"</c:if>/>
                </div>
            </div>

            <div class="form-group">
                <label for="respDataTypeLabel" class="col-sm-2 control-label">默认响应数据类型</label>
                <div class="col-sm-5" id="respDataTypeLabel">
                    <div>
                        <label class="radio-inline">
                            <input type="radio" name="respDataType" id="respDataType_JSON" value="JSON" <c:if test="${responseDataType == 'JSON'}">checked</c:if>> JSON
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="respDataType" id="respDataType_String" value="String" <c:if test="${responseDataType == 'String'}">checked</c:if>> String
                        </label>
                    </div>
                </div>
            </div>

            <div class="form-group" data-content="box" id="JSON_Box">
                <label for="respData4JSON" class="col-sm-2 control-label">默认响应数据</label>
                <div id="respData4JSON" style="height: 50%" class="col-sm-5"></div>
            </div>

            <div class="form-group" data-content="box" id="String_Box" style="display: none;">
                <label for="respData4String" class="col-sm-2 control-label">默认响应数据</label>
                <div class="col-sm-5">
                    <textarea id="respData4String" style="height: 50%" class="form-control">${defaRespData}</textarea>
                </div>
            </div>
        </form>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="submit" type="button" class="btn btn-primary">提交</button>
            </div>
        </div>
    </div>
</div>

<script>
    var container, options, json, editor;
    container = document.getElementById('respData4JSON');
    options = {
        mode: 'text',
        modes: ['code', 'text', 'form', 'tree', 'view'], // allowed modes
        onError: function (err) {
            alert(err.toString());
        },
        onChange: function () {
            console.log('change');
        },
        indentation: 4,
        escapeUnicode: false
    };
    if(${responseDataType == 'JSON'} ){
        <c:if test="${defaRespData != null and defaRespData != ''}">json = ${defaRespData};</c:if>
    }else{
        json = {};
    }
    editor = new JSONEditor(container, options, json);
    editor.getText();
</script>
</body>
</html>