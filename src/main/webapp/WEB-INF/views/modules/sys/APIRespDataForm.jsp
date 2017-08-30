<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>配置服务响应数据</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <%--jsoneditor--%>
    <script src="${ctxStatic}/jsoneditor/jsoneditor.js" type="text/javascript"></script>
    <link href="${ctxStatic}/jsoneditor/jsoneditor.css" rel="stylesheet"/>
    <script type="text/javascript">
        $(function () {
            $('#systemName').on('select2:close', function () {
                $("#serverName").empty();
                $("#operationName").empty();
                var systemName = $('#systemName option:selected').val();
                if (systemName && systemName.length > 0) {
                    $.ajax({
                        url: ctx + '/Mock/getMockServer',
                        type: 'post',
                        dataType: 'json',
                        data: {"mockSystemName": systemName},
                        success: function (response) {
                            if (response) {
                                if (response.success) {
                                    var data = response.data;
                                    var items = [];
                                    for (var index in data) {
                                        var item = {id: data[index], text: data[index]};
                                        items.push(item);
                                    }
                                    $('#serverName').append("<option value=''></option>")
                                    $('#serverName').select2({data: items});
                                }
                            }
                        }
                    });
                }
            });

            $('#serverName').on('select2:close', function () {
                $("#operationName").empty();
                var systemName = $('#systemName option:selected').val();
                var serverName = $('#serverName option:selected').val();
                if (systemName && systemName.length > 0 && serverName && serverName.length > 0) {
                    $.ajax({
                        url: ctx + '/Mock/getMockOperation',
                        type: 'post',
                        dataType: 'json',
                        data: {"mockSystemName": systemName, "mockServerName": serverName},
                        success: function (response) {
                            if (response) {
                                if (response.success) {
                                    var data = response.data;
                                    var items = [];
                                    for (var index in data) {
                                        var item = {id: data[index], text: data[index]};
                                        items.push(item);
                                    }
                                    $('#operationName').append("<option value=''></option>")
                                    $('#operationName').select2({data: items});
                                }
                            }
                        }
                    });
                }
            });


            $('#operationName').on('select2:close', function () {
                var boxSuffix = "_Box";
                $("div[data-content=box]").hide();
                $("#respDataType").val("");
                editor.setText("{}");
                //editor = new JSONEditor(container, options, json);
                $("#respData4String").val("");
                var systemName = $('#systemName option:selected').val();
                var serverName = $('#serverName option:selected').val();
                var operationName = $('#operationName option:selected').val();
                if (systemName && systemName.length > 0 && serverName && serverName.length > 0 && operationName && operationName.length > 0) {
                    $.ajax({
                        url: ctx + '/Mock/getRequestAndResponse',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            "mockSystemName": systemName,
                            "mockServerName": serverName,
                            "mockOperationName": operationName
                        },
                        success: function (response) {
                            if (response) {
                                if (response.success) {
                                    var data = response.data;
                                    if (data) {
                                        if (data.requestProcessor && data.requestProcessor.responseDataType) {
                                            $("#respDataType").val(data.requestProcessor.responseDataType);
                                            var boxId = data.requestProcessor.responseDataType + boxSuffix;
                                            $("#" + boxId).show();
                                            if(data.responseData && data.responseData.responseData){
                                                var responseData = data.responseData.responseData;
                                                if(data.requestProcessor.responseDataType == 'JSON'){
                                                    editor.setText(responseData);
                                                }else if(data.requestProcessor.responseDataType == 'String'){
                                                    $("#respData4String").val(responseData);
                                                }
                                            }
                                        } else {
                                            showInfo("该操作的相应数据类型获取失败，请配置！", "Hello");
                                        }

                                    }
                                }
                            }
                        }
                    });
                }
            });


            $("#deployRspData").on('click', function () {
                var systemName = $('#systemName option:selected').val();
                var serverName = $('#serverName option:selected').val();
                var operationName = $('#operationName option:selected').val();
                if (!systemName || !serverName || !operationName
                        || systemName.length < 0 || serverName.length < 0
                        || operationName.length < 0) {
                    showInfo("系统名称，服务名称和操作名称均不能为空！", "配置服务", 5000);
                    return;
                }
                var responseData = $("#respDataType").val() == 'JSON' ? editor.getText() : $('#respData4String').val();
                $.ajax({
                    url: '${ctx}/api/respDataDeploy',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        "systemName": $('#systemName option:selected').val(),
                        "serverName": $('#serverName option:selected').val(),
                        "operationName": $("#operationName option:selected").val(),
                        "responseData": responseData
                    },
                    success: function (data) {
                        if (data && data.success) {
                            showConfirm(data.message, "配置服务响应数据", 5000);
                        }
                    }
                });
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
            <li>
                <a href="${ctx}/api/add/form">API添加</a>
            </li>
            <li class="active">
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
                    <select class="form-control" id="systemName">
                        <option value=""></option>
                        <c:forEach items="${mockSystemList}" var="item">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="serverName" class="col-sm-2 control-label">服务</label>
                <div class="col-sm-5">
                    <select class="form-control" id="serverName">
                        <option value=""></option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="operationName" class="col-sm-2 control-label">操作</label>
                <div class="col-sm-5">
                    <select class="form-control" id="operationName">
                        <option value=""></option>
                    </select>
                </div>
            </div>

            <input type="hidden" id="respDataType"/>

            <div class="form-group" data-content="box" id="JSON_Box">
                <label for="respData4JSON" class="col-sm-2 control-label">响应数据</label>
                <div id="respData4JSON" style="height: 50%" class="col-sm-5"></div>
            </div>

            <div class="form-group" data-content="box" id="String_Box" style="display: none;">
                <label for="respData4String" class="col-sm-2 control-label">响应数据</label>
                <div class="col-sm-5">
                    <textarea id="respData4String" style="height: 50%" class="form-control">${defaRespData}</textarea>
                </div>
            </div>
        </form>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="deployRspData" type="button" class="btn btn-primary">提交</button>
            </div>
        </div>
    </div>
</div>
<script>
    var container, options, json, editor;
    container = document.getElementById('respData4JSON');
    options = {
        mode: 'code',
        modes: ['code', 'form', 'text', 'tree', 'view'], // allowed modes
        onError: function (err) {
            alert(err.toString());
        },
        onChange: function () {
            console.log('change');
        },
        indentation: 4,
        escapeUnicode: false
    };
    json = {};
    editor = new JSONEditor(container, options, json);
</script>
</body>
</html>