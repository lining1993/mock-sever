package org.qianshengqian.mockserver.service;

import org.qianshengqian.common.config.MockAPIMapping;
import org.qianshengqian.mockserver.bean.*;
import org.qianshengqian.common.config.Const;
import org.qianshengqian.common.config.MockAPIResponseDataMapping;
import org.qianshengqian.common.mapper.JsonMapper;
import org.qianshengqian.common.utils.FileUtils;
import org.qianshengqian.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/29.
 */
@Service
public class APIResponseDataService {
    private static Logger logger = LoggerFactory.getLogger(APIResponseDataService.class);
    @Autowired
    ProcessorService processorService;

    public boolean setResponetData(String systemName,String serverName,String operationName,String responseData){
        String fileName = Const.getRspDataFileName(systemName,serverName,operationName)+Const.JSON_FILE_SUFFIX;
        String filePath = Const.getRspdataFileBaseUrl() +fileName;
        MockAPIResponseData mockAPIResponseData = new MockAPIResponseData();
        mockAPIResponseData.setDeployDate(new Date());
        mockAPIResponseData.setResponseData(responseData);
        String fileContent = JsonMapper.toJsonString(mockAPIResponseData);
        try {
            File jsonFile = new File(filePath);
            if (!jsonFile.exists()) {
                if (!jsonFile.getParentFile().exists()) {
                    jsonFile.getParentFile().mkdirs();
                }
            }
            File file = new File(filePath);
            FileUtils.writeToFile(jsonFile.getPath(), fileContent, "UTF-8", false);
            String key = Const.getRspDataFileName(systemName,serverName,operationName);
            MockAPIResponseDataMapping.respDataMap.put(key,mockAPIResponseData);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getResponetData(String systemName,String serverName,String operationName){
        String responseData = getResponseDataFromMockAPIRespData(systemName,serverName,operationName);
        if(StringUtils.isNullOrEmpty(responseData)){
            responseData = getDefaultResponseData(systemName,serverName,operationName);
        }
        return responseData;
    }

    public String getResponseDataFromMockAPIRespData(String systemName,String serverName,String operationName){
        MockAPIResponseData responseData = getMockAPIResponseData(systemName,serverName,operationName);
        if(responseData != null){
            return responseData.getResponseData();
        }
        return null;
    }


    public String getDefaultResponseData(String systemName,String serverName,String operationName){
        RequestProcessor requestProcessor = processorService.getRequestProcessor(systemName,serverName,operationName);
        if(requestProcessor != null){
            return requestProcessor.getDefaultResponseData();
        }
        return null;
    }

    public MockAPIResponseData getMockAPIResponseData(String mockSystemName,String mockServerName,String mockOperationName){
        String datakey = Const.getRspDataFileName(mockSystemName, mockServerName, mockOperationName);
        MockAPIResponseData responseData = MockAPIResponseDataMapping.respDataMap.get(datakey);
        if(responseData == null){
            String filePath = Const.getRspdataFileBaseUrl() + datakey + Const.JSON_FILE_SUFFIX;
            try {
                File file = new File(filePath);
                if(file.exists()){
                    String responseDataStr = FileUtils.readFile(filePath, "UTF-8");
                    if(!StringUtils.isNullOrEmpty(responseDataStr)){
                        responseData = (MockAPIResponseData) JsonMapper.fromJsonString(responseDataStr, MockAPIResponseData.class);
                    }
                }
            }catch (Exception e){
                logger.error("=== 获取processor失败！filepath:{};异常信息:{}",filePath,e.getLocalizedMessage());
            }
        }
        return responseData;
    }
}
