<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>Mock-server</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <style type="text/css">
        /************ Shortcut Buttons ************/
        ul {
            list-style: none;
        }

        .shortcut-button {
            border: 1px solid #ccc;
            border-radius: 6px;
            background: #f7f7f7 url('${ctxStatic}/images/shortcut-button-bg.gif') top left no-repeat;
            display: block;
            width: 120px;
            margin: 0 0 20px 0;
        }

        .shortcut-button span {
            -moz-border-radius: 7px;
            /* -webkit-border-radius: 7px; */
            border-radius: 7px;
        }

        .shortcut-button span {
            border: 1px solid #fff;
            display: block;
            padding: 15px 10px 15px 10px;
            text-align: center;
            color: #555;
            font-size: 13px;
            line-height: 1.3em;
        }

        .shortcut-button span img {
            margin-bottom: 10px;
        }

        .shortcut-button:hover {
            background: #fff;

        }

        .shortcut-button span:hover {
            color: #57a000;
        }

        ul.shortcut-buttons-set li {
            float: left;
            margin: 0 15px 0 0;
            padding: 0 !important;
            background: 0;
        }

    </style>
</head>
<body style="background-color: #d2d6de">
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="jumbotron well" style="background-color: #5D478B;color: white;">
                <h1>Mock-API</h1>

                <p>给的再多，不如懂我！</p>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="panel panel-info" style="min-height: 70%;">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        You can do this
                    </h3>
                </div>
                <div class="panel-body">
                    <ul class="shortcut-buttons-set">
                        <li>
                            <a class="shortcut-button" href="${ctx}/api/list">
                                <span>
                                    <img src="${ctxStatic}/images/icons/List.png" alt="icon">
                                    <br>API列表
                                </span>
                            </a>
                        </li>
                        <li>
                            <a class="shortcut-button" href="${ctx}/api/add/form">
                            <span>
                                <img src="${ctxStatic}/images/icons/Add.png" alt="icon">
                                <br>API添加
                            </span>
                            </a>
                        </li>
                        <li>
                            <a class="shortcut-button" href="${ctx}/api/respDataForm">
                            <span>
                                <img src="${ctxStatic}/images/icons/Deploy.png" alt="icon">
                                <br>响应数据
                            </span>
                            </a>
                        </li>
                        <li>
                            <a class="shortcut-button api_refresh" href="#">
                            <span>
                                <img src="${ctxStatic}/images/icons/Refresh.png" alt="icon">
                                <br>API刷新
                            </span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>


</div>

</body>
</html>