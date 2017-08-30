package org.qianshengqian.common.config;

/**
 * Created by lining on 2017/8/18.
 */
public class Const {
    public static final String MOCK_API_PREFIX = "/mockURL";
    public static final String MOCK_API_BASE_URL = "mock.api.base.url";

    public static final String USER_FILE_BASE_URL = "mockAPIFiles";//文件基础路径
    public static final String RSPDATA_FILE_BASE_URL = "/rspDataFiles/";//响应数据存放路径
    public static final String PROCESSPR_FILE_BASE_URL = "/processprFiles/";//服务处理类存放路径
    public static final String JSON_FILE_SUFFIX = ".json";
    public static String getRspdataFileBaseUrl(){
        return USER_FILE_BASE_URL+ RSPDATA_FILE_BASE_URL;
    }
    public static String getProcessprFileBaseUrl(){
        return USER_FILE_BASE_URL+PROCESSPR_FILE_BASE_URL;
    }
    public static String getRspDataFileName(String systemName,String serverName,String operationName){
        return String.format("%s-%s-%s-data",systemName,serverName,operationName);
    }
    public static String getProcFileName(String systemName,String serverName,String operationName){
        return String.format("%s-%s-%s-processor",systemName,serverName,operationName);
    }
}
