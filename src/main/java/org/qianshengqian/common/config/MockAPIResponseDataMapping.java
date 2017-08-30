package org.qianshengqian.common.config;

import org.qianshengqian.mockserver.bean.MockAPIResponseData;
import org.qianshengqian.common.mapper.JsonMapper;
import org.qianshengqian.common.utils.FileUtils;
import org.qianshengqian.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/29.
 */
@Component
public class MockAPIResponseDataMapping {
    private static Logger logger = LoggerFactory.getLogger(MockAPIMapping.class);
    public static Map<String,MockAPIResponseData> respDataMap = new HashMap<>();
    public static Map<String,List<MockAPIResponseData>> historyRespDataMap = new HashMap<>();

    static {
        initRespDataMap();
    }
    public static void refreshAPIRespData(){
        initRespDataMap();
    }
    public static void initRespDataMap(){
        respDataMap = new HashMap<>();
        String filepath = Const.getRspdataFileBaseUrl();
        File file = new File(filepath);
        if(file.exists()){
            File[] files = file.listFiles();
            for(File temp : files){
                if(temp.isFile()){
                    String fileName = temp.getName();
                    fileName = fileName.replaceAll(Const.JSON_FILE_SUFFIX,"");
                    MockAPIResponseData responseData  = getRespData(temp.getPath());
                    respDataMap.put(fileName,responseData);
                }
            }
        }
    }

    private static MockAPIResponseData getRespData(String filepath){
        MockAPIResponseData responseData = null;
        try {
            String responseDataStr = FileUtils.readFile(filepath, "UTF-8");
            if(!StringUtils.isNullOrEmpty(responseDataStr)){
                responseData = (MockAPIResponseData) JsonMapper.fromJsonString(responseDataStr,MockAPIResponseData.class);
            }
        }catch (Exception e){
            logger.error("=== 获取processor失败！filepath:{};异常信息:{}",filepath,e.getLocalizedMessage());
        }
        return responseData;
    }
}
