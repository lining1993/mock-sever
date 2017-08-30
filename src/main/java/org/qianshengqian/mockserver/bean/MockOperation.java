package org.qianshengqian.mockserver.bean;

/**
 * Created by Administrator on 2017/8/22.
 */
public class MockOperation {
    private String name;
    private String cnName;
    private RequestProcessor requestProcessor;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public RequestProcessor getRequestProcessor() {
        return requestProcessor;
    }

    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }
}
