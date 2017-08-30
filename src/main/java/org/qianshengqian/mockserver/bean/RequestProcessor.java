package org.qianshengqian.mockserver.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/21.
 */
public class RequestProcessor implements Serializable {
    private String systemName;
    private String serverName;
    private String operationName;
    private String method;
    private Map<String,RequestParameter> parameterMap;
    private String responseDataType;
    private String defaultResponseData;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, RequestParameter> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, RequestParameter> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getResponseDataType() {
        return responseDataType;
    }

    public void setResponseDataType(String responseDataType) {
        this.responseDataType = responseDataType;
    }

    public String getDefaultResponseData() {
        return defaultResponseData;
    }

    public void setDefaultResponseData(String defaultResponseData) {
        this.defaultResponseData = defaultResponseData;
    }
}
