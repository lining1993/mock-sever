package org.qianshengqian.mockserver.service;

import org.apache.commons.collections.map.HashedMap;
import org.qianshengqian.common.config.Const;
import org.qianshengqian.common.config.MockAPIMapping;
import org.qianshengqian.common.config.MockAPIResponseDataMapping;
import org.qianshengqian.common.mapper.JsonMapper;
import org.qianshengqian.common.utils.FileUtils;
import org.qianshengqian.common.utils.StringUtils;
import org.qianshengqian.mockserver.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lining on 2017/8/29.
 */
@Service
public class MockService {
    private static Logger logger = LoggerFactory.getLogger(MockService.class);

    public List<String> getMockSystem(){
        List<String> mockSystemList = new ArrayList<>();
        for(Map.Entry<String,MockSystem> entry: MockAPIMapping.mockSystemMap.entrySet()){
            mockSystemList.add(entry.getKey());
        }
        return mockSystemList;
    }


    public List<String> getMockServer(String mockSystemName){
        List<String> mockServerList = new ArrayList<>();
        MockSystem mockSystem = MockAPIMapping.mockSystemMap.get(mockSystemName);
        if(mockSystem != null && !mockSystem.getMockServerMap().isEmpty()){
            Map<String,MockServer> mockServerMap = mockSystem.getMockServerMap();
            for(Map.Entry<String,MockServer> entry: mockServerMap.entrySet()){
                mockServerList.add(entry.getKey());
            }
        }
        return mockServerList;
    }


    public List<String> getMockOperation(String mockSystemName,String mockServerName){
        List<String> mockOperationList = new ArrayList<>();
        MockSystem mockSystem = MockAPIMapping.mockSystemMap.get(mockSystemName);
        if(mockSystem != null && !mockSystem.getMockServerMap().isEmpty()){
            Map<String,MockServer> mockServerMap = mockSystem.getMockServerMap();
            MockServer mockServer = mockServerMap.get(mockServerName);
            if(mockServer != null && !mockServer.getOperationHashMap().isEmpty()){
                Map<String,MockOperation> mockOperationMap = mockServer.getOperationHashMap();
                for(Map.Entry<String,MockOperation> entry: mockOperationMap.entrySet()){
                    mockOperationList.add(entry.getKey());
                }
            }
        }
        return mockOperationList;
    }


    public Map<String,Object> getRequestAndResponse(String mockSystemName,String mockServerName,String mockOperationName){
        Map<String,Object> requestAndResponseMap = new HashedMap();
        MockSystem mockSystem = MockAPIMapping.mockSystemMap.get(mockSystemName);
        if(mockSystem != null && !mockSystem.getMockServerMap().isEmpty()){
            Map<String,MockServer> mockServerMap = mockSystem.getMockServerMap();
            MockServer mockServer = mockServerMap.get(mockServerName);
            if(mockServer != null && !mockServer.getOperationHashMap().isEmpty()){
                Map<String,MockOperation> mockOperationMap = mockServer.getOperationHashMap();
                MockOperation mockOperation = mockOperationMap.get(mockOperationName);
                RequestProcessor requestProcessor = mockOperation.getRequestProcessor();
                requestAndResponseMap.put("requestProcessor",requestProcessor);
            }
        }
        MockAPIResponseData responseData = MockAPIResponseDataMapping.respDataMap.get(Const.getRspDataFileName(mockSystemName, mockServerName, mockOperationName));
        if(responseData != null){
            requestAndResponseMap.put("responseData",responseData);
        }
        return requestAndResponseMap;
    }



}
