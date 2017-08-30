package org.qianshengqian.mockserver.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/22.
 */
public class MockSystem {

    private String name;
    private String cnName;
    private Map<String,MockServer> mockServerMap = new HashMap<>();

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

    public Map<String, MockServer> getMockServerMap() {
        return mockServerMap;
    }

    public void setMockServerMap(Map<String, MockServer> mockServerMap) {
        this.mockServerMap = mockServerMap;
    }
}
