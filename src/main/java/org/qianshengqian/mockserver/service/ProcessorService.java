package org.qianshengqian.mockserver.service;

import org.qianshengqian.common.config.MockAPIMapping;
import org.qianshengqian.mockserver.bean.MockOperation;
import org.qianshengqian.mockserver.bean.MockServer;
import org.qianshengqian.mockserver.bean.MockSystem;
import org.qianshengqian.mockserver.bean.RequestProcessor;
import org.qianshengqian.common.config.Const;
import org.qianshengqian.common.mapper.JsonMapper;
import org.qianshengqian.common.utils.FileUtils;
import org.qianshengqian.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/21.
 */
@Service
public class ProcessorService {
    private static Logger logger = LoggerFactory.getLogger(ProcessorService.class);

    public static boolean setRequestProcessor(String systemName,String serverName,String operationName,String responseDataType,String defaultResponseData){
        String fileName = Const.getProcFileName(systemName,serverName,operationName)+Const.JSON_FILE_SUFFIX;
        String filePath = Const.getProcessprFileBaseUrl()+fileName;
        RequestProcessor processor = new RequestProcessor();
        processor.setSystemName(systemName);
        processor.setServerName(serverName);
        processor.setOperationName(operationName);
        processor.setResponseDataType(responseDataType);
        processor.setDefaultResponseData(defaultResponseData);
        try {
            File jsonFile = new File(filePath);
            if (!jsonFile.exists()) {
                if (!jsonFile.getParentFile().exists()) {
                    jsonFile.getParentFile().mkdirs();
                }
            }
            File file = new File(filePath);
            String data = JsonMapper.toJsonString(processor);
            FileUtils.writeToFile(jsonFile.getPath(), data, "UTF-8", false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public RequestProcessor getRequestProcessor(String systemName ,String serverName,String operationName){
        //从全局配置文件获取RequestProcessor
        MockSystem mockSystem = MockAPIMapping.mockSystemMap.get(systemName);
        if(mockSystem != null){
            MockServer mockServer = mockSystem.getMockServerMap().get(serverName);
            if(mockServer != null){
                MockOperation mockOperation = mockServer.getOperationHashMap().get(operationName);
                if(mockOperation != null){
                    RequestProcessor requestProcessor = mockOperation.getRequestProcessor();
                    if(requestProcessor != null){
                        return requestProcessor;
                    }
                }
            }
        }

        //从文件中获取RequestProcessor
        String fileName = Const.getProcFileName(systemName,serverName,operationName);
        String filepath = Const.getProcessprFileBaseUrl()+fileName+Const.JSON_FILE_SUFFIX;
        try {
            File file = new File(filepath);
            if(file.exists()){
                String processorData = FileUtils.readFile(filepath, "UTF-8");
                if(!StringUtils.isNullOrEmpty(processorData)){
                    RequestProcessor requestProcessor = (RequestProcessor)JsonMapper.fromJsonString(processorData,RequestProcessor.class);
                    if(requestProcessor != null){
                        return requestProcessor;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("=== 读取数据异常：文件路径:{};异常信息:{};",filepath , e.getMessage());
        }

        return null;

    }


}
