package org.qianshengqian.common.config;

import org.qianshengqian.common.mapper.JsonMapper;
import org.qianshengqian.common.utils.FileUtils;
import org.qianshengqian.common.utils.StringUtils;
import org.qianshengqian.mockserver.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/8/22.
 */
@Component
public class MockAPIMapping {
    private static Logger logger = LoggerFactory.getLogger(MockAPIMapping.class);
    public static Map<String,MockSystem> mockSystemMap = new HashMap<>();
    public static List<MockAPI> mockAPIList = new ArrayList<>();
    public static Map<Integer,MockAPI> mockAPIMap = new HashMap<>();
    static {
        initMockAPIMapping();
        initMockAPIList();
        initMockAPIMap();
    }
    public static void refreshAPI(){
        initMockAPIMapping();
        initMockAPIList();
        initMockAPIMap();
    }

    public static void initMockAPIMapping(){
        mockSystemMap = new HashMap<>();
        String filepath = Const.getProcessprFileBaseUrl();
        File file = new File(filepath);
        if(file.exists()){
            File[] files = file.listFiles();
            for(File temp : files){
                if(temp.isFile()){
                    String fileName = temp.getName();
                    RequestProcessor processor  = getProcessor(temp.getPath());
                    String[] params =fileName.split("-");
                    if(params.length==4){
                        String systemName = params[0];
                        String serverName = params[1];
                        String operationName = params[2];
                        MockSystem mockSystem = mockSystemMap.get(systemName);
                        mockSystem = mockSystem==null ? new MockSystem():mockSystem;
                        MockServer mockServer = mockSystem.getMockServerMap().get(serverName);
                        mockServer = mockServer==null ? new MockServer():mockServer;
                        MockOperation mockOperation = mockServer.getOperationHashMap().get(operationName);
                        mockOperation = mockOperation==null ? new MockOperation() : mockOperation;
                        mockOperation.setName(operationName);
                        mockOperation.setRequestProcessor(processor);
                        mockServer.setName(serverName);
                        mockSystem.setName(systemName);
                        mockServer.getOperationHashMap().put(operationName,mockOperation);
                        mockSystem.getMockServerMap().put(serverName,mockServer);
                        mockSystemMap.put(systemName,mockSystem);
                    }
                }
            }
        }
    }

    private static RequestProcessor getProcessor(String filepath){
        RequestProcessor processor = null;
        try {
            String processorData = FileUtils.readFile(filepath, "UTF-8");
            if(!StringUtils.isNullOrEmpty(processorData)){
                processor = (RequestProcessor)JsonMapper.fromJsonString(processorData,RequestProcessor.class);
            }
        }catch (Exception e){
            logger.error("=== 获取processor失败！filepath:{};异常信息:{}",filepath,e.getLocalizedMessage());
        }
        return processor;

    }

    public static void initMockAPIList(){
        mockAPIList = new ArrayList<>();
        MockAPI mockAPI = null;
        int ancestorId=0,sysId=1,servId=1000,operId = 10000;
        for (Map.Entry<String, MockSystem> sysEntry : mockSystemMap.entrySet()) {
            MockSystem mockSystem = sysEntry.getValue();
            mockAPI = new MockAPI<MockSystem>(sysId,ancestorId,sysEntry.getKey(),mockSystem);
            mockAPIList.add(mockAPI);
            for(Map.Entry<String, MockServer> servEntry:mockSystem.getMockServerMap().entrySet()){
                MockServer mockServer = servEntry.getValue();
                mockAPI = new MockAPI<MockServer>(servId,sysId,servEntry.getKey(),mockServer);
                mockAPIList.add(mockAPI);
                for(Map.Entry<String, MockOperation> operEntry:mockServer.getOperationHashMap().entrySet()){
                    mockAPI = new MockAPI<MockOperation>(operId,servId,operEntry.getKey(),operEntry.getValue());
                    mockAPIList.add(mockAPI);
                    operId++;
                }
                servId++;
            }
            sysId++;
        }
    }

    public static void initMockAPIMap(){
        mockAPIMap = new HashMap<>();
        mockAPIMap = mockAPIList.stream().collect(
                Collectors.toMap(MockAPI::getId, (p) -> p));
    }


}
