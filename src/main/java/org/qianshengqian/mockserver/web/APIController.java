package org.qianshengqian.mockserver.web;

import org.qianshengqian.mockserver.bean.MockAPI;
import org.qianshengqian.mockserver.bean.MockOperation;
import org.qianshengqian.mockserver.bean.MockSystem;
import org.qianshengqian.common.config.Const;
import org.qianshengqian.common.config.Global;
import org.qianshengqian.common.config.MockAPIMapping;
import org.qianshengqian.common.config.MockAPIResponseDataMapping;
import org.qianshengqian.mockserver.service.APIResponseDataService;
import org.qianshengqian.mockserver.service.APIService;
import org.qianshengqian.mockserver.service.ProcessorService;
import org.qianshengqian.common.web.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lining on 2017/8/29.
 */
@Controller
@RequestMapping("/api")
public class APIController {
    @Autowired
    APIResponseDataService responseDataService;
    @Autowired
    APIService apiService;

    @RequestMapping("/list")
    public String apiListView(Model model){
        model.addAttribute("apiList", MockAPIMapping.mockAPIList);
        return "modules/sys/APIList";
    }

    @RequestMapping("/add/form")
    public String apiAddFormView(Model model,Integer sysId,Integer servId,Integer operId){
        Map<Integer,MockAPI> mockAPIMap = MockAPIMapping.mockAPIMap;
        if(operId != null){
            MockAPI operAPI = mockAPIMap.get(operId);
            MockAPI servAPI = mockAPIMap.get(operAPI.getParentId());
            MockAPI sysAPI = mockAPIMap.get(servAPI.getParentId());
            MockOperation mockOperation = (MockOperation) operAPI.getSubject();
            model.addAttribute("sysName",sysAPI.getName());
            model.addAttribute("servName",servAPI.getName());
            model.addAttribute("operName",operAPI.getName());
            System.out.println(mockOperation.getRequestProcessor().getDefaultResponseData());
            model.addAttribute("responseDataType",mockOperation.getRequestProcessor().getResponseDataType());
            model.addAttribute("defaRespData",mockOperation.getRequestProcessor().getDefaultResponseData());
        }else if(servId != null){
            MockAPI servAPI = mockAPIMap.get(servId);
            MockAPI sysAPI = mockAPIMap.get(servAPI.getParentId());
            model.addAttribute("sysName",sysAPI.getName());
            model.addAttribute("servName",servAPI.getName());
            model.addAttribute("defaRespData","{}");
        }else if(sysId != null){
            MockAPI sysAPI = mockAPIMap.get(sysId);
            model.addAttribute("sysName",sysAPI.getName());
            model.addAttribute("defaRespData","{}");
        }else{
            model.addAttribute("defaRespData","{}");
        }
        return "modules/sys/APIAddForm";
    }

    @RequestMapping("/add")
    @ResponseBody
    public CommonResponse addAPI(HttpServletRequest request,String systemName, String serverName, String operationName,String responseDataType, String defaultResponseData) {
        String url = apiService.addAPI(systemName, serverName, operationName, responseDataType, defaultResponseData);
        if(url != null){
            return CommonResponse.ReturnCommonBaseResponse(true, "配置成功!", Global.getConfig(Const.MOCK_API_BASE_URL) + request.getContextPath() + url);
        }
        return CommonResponse.ReturnCommonBaseResponse(false, "配置失败！", null);
    }

    @RequestMapping("/refresh")
    @ResponseBody
    public CommonResponse apiRefresh(){
        MockAPIMapping.refreshAPI();
        MockAPIResponseDataMapping.refreshAPIRespData();
        return CommonResponse.ReturnCommonBaseResponse(true,"刷新成功！",null);
    }

    @RequestMapping("/respDataForm")
    public String APIRespDataFormView(Model model){
        List<String> mockSystemList = new ArrayList<>();
        for(Map.Entry<String,MockSystem> entry: MockAPIMapping.mockSystemMap.entrySet()){
            mockSystemList.add(entry.getKey());
        }
        model.addAttribute("mockSystemList", mockSystemList);
        return "modules/sys/APIRespDataForm";
    }

    @RequestMapping("/respDataDeploy")
    @ResponseBody
    public CommonResponse APIRespDataDeploy(String systemName,String serverName,String operationName,String responseData){
        if(responseDataService.setResponetData(systemName,serverName,operationName,responseData)){
            MockAPIResponseDataMapping.refreshAPIRespData();
            return CommonResponse.ReturnCommonBaseResponse(true,"配置成功！",null);
        }

        return CommonResponse.ReturnCommonBaseResponse(false,"配置失败！",null);
    }

}
