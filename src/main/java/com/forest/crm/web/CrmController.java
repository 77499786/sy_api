package com.forest.crm.web;

import com.forest.core.BaseController;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.crm.service.CrmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by CodeGenerator on 2018/04/30.
*/
@RestController
@RequestMapping("/crm")
public class CrmController extends BaseController {
    @Resource
    private CrmService crmService;

    @PostMapping("/getcrmdatas")
    public Result getcrmchartdatas(@RequestParam(value = "datatype") String datatype, HttpServletRequest request) {
//        String datatype = request.getParameter("datatype");
        if(StringUtils.isBlank(datatype)){
            return ResultGenerator.genFailResult("请设置查询参数");
        }
        Map<String, Object > param = new HashMap<String, Object>();
        param.put("datatype", datatype);
        List<Map<String, Object>> list = crmService.callCrmDatas(param);
        return ResultGenerator.genSuccessResult(list);
    }

}
