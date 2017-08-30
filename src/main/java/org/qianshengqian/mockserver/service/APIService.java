package org.qianshengqian.mockserver.service;

import org.qianshengqian.common.config.Const;
import org.qianshengqian.common.config.Global;
import org.qianshengqian.common.config.MockAPIMapping;
import org.qianshengqian.common.web.CommonResponse;
import org.springframework.stereotype.Service;

/**
 * Created by lining on 2017/8/29.
 */
@Service
public class APIService {


    public String addAPI(String systemName, String serverName, String operationName,String responseDataType, String defaultResponseData){
        if (ProcessorService.setRequestProcessor(systemName, serverName, operationName, responseDataType, defaultResponseData)) {
            MockAPIMapping.refreshAPI();
            String url = Const.MOCK_API_PREFIX
                    + "/" + systemName
                    + "/" + serverName
                    + "/" + operationName;
            return url;
        }
        return null;
    }

    public void checkAPI(){

    }
}
