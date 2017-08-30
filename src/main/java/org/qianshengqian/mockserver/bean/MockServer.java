package org.qianshengqian.mockserver.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/22.
 */
public class MockServer {

    private String name;
    private String cnName;
    private Map<String,MockOperation> operationHashMap = new HashMap<>();

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

    public Map<String, MockOperation> getOperationHashMap() {
        return operationHashMap;
    }

    public void setOperationHashMap(Map<String, MockOperation> operationHashMap) {
        this.operationHashMap = operationHashMap;
    }
}
