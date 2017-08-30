package org.qianshengqian.mockserver.web;

import org.qianshengqian.common.web.CommonResponse;
import org.qianshengqian.mockserver.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by lining on 2017/8/29.
 */
@Controller
@RequestMapping("/Mock")
public class MockController {

    @Autowired
    MockService mockService;

    @RequestMapping("/getMockSystem")
    @ResponseBody
    public CommonResponse getMockSystem() {
        List<String> data = mockService.getMockSystem();
        return CommonResponse.ReturnCommonBaseResponse(true, "获取成功！", data);
    }

    @RequestMapping("/getMockServer")
    @ResponseBody
    public CommonResponse getMockServer(String mockSystemName) {
        List<String> data = mockService.getMockServer(mockSystemName);
        return CommonResponse.ReturnCommonBaseResponse(true, "获取成功！", data);
    }

    @RequestMapping("/getMockOperation")
    @ResponseBody
    public CommonResponse getMockOperation(String mockSystemName, String mockServerName) {
        List<String> data = mockService.getMockOperation(mockSystemName, mockServerName);
        return CommonResponse.ReturnCommonBaseResponse(true, "获取成功！", data);
    }

    @RequestMapping("/getRequestAndResponse")
    @ResponseBody
    public CommonResponse getRequestAndResponse(String mockSystemName, String mockServerName, String mockOperationName) {
        Map<String,Object> data = mockService.getRequestAndResponse(mockSystemName, mockServerName, mockOperationName);
        return CommonResponse.ReturnCommonBaseResponse(true, "获取成功！", data);
    }

}
