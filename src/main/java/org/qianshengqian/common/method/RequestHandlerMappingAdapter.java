package org.qianshengqian.common.method;

import org.qianshengqian.mockserver.bean.RequestProcessor;
import org.qianshengqian.common.config.Const;
import org.qianshengqian.mockserver.service.APIResponseDataService;
import org.qianshengqian.mockserver.service.ProcessorService;
import org.qianshengqian.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/21.
 */
@Component
public class RequestHandlerMappingAdapter {
    protected static Logger logger = LoggerFactory.getLogger(RequestHandlerMappingAdapter.class);

    @Autowired
    APIResponseDataService responseDataService;

    public String requestAdapter(HttpServletRequest request) {
        String requestURL = request.getRequestURI();
        requestURL = requestURL.substring(requestURL.indexOf(Const.MOCK_API_PREFIX));
        if(StringUtils.startsWithIgnoreCase(requestURL,Const.MOCK_API_PREFIX)){
            String [] urlArr = requestURL.split("/");
            if(urlArr.length >= 5){
                String systemName = urlArr[2];
                String serverName = urlArr[3];
                String operationName = urlArr[4];
                String responseData = responseDataService.getResponetData(systemName, serverName, operationName);
                return responseDataHand(responseData);
            }
        }
        return null;
    }

    private static String responseDataHand(String responseData){
        //去除末尾换行符
        int index = responseData.lastIndexOf("\n");
        if(index == responseData.length()-1){
            responseData = responseData.substring(0, responseData.length()-1);
        }
        return responseData;
    }





}
