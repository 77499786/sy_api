package com.forest.sy.web;

import com.forest.core.BaseController;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.crm.service.CrmService;
import com.forest.project.model.FrameEmployee;
import com.forest.project.service.FrameEmployeeService;
import com.forest.project.service.FrameOrganizationService;
import com.forest.sy.model.SyQuery;
import com.forest.sy.model.SyWeituo;
import com.forest.sy.service.SyService;
import com.forest.utils.ResourceUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by CodeGenerator on 2018/04/30.
*/
@RestController
@RequestMapping("/sy")
public class SyController extends BaseController {
    @Resource
    private SyService syService;
    @Resource
    private FrameEmployeeService frameEmployeeService;

    @PostMapping("/getsydatas")
    public Result getsydatas(@RequestBody() @Valid PageInfo<SyQuery> pageInfo,
                             HttpServletRequest request) {
        String datatype = (String)super.getItem(pageInfo, "datatype");
        super.getUserAccount();
        if(Strings.isNullOrEmpty(datatype)){
            return ResultGenerator.genFailResult("请设置查询参数");
        }
        if ("query".equals(datatype)) {
            return getSyDataBySql(pageInfo);
        }
        PageInfo<Map<String, Object>> ret = new PageInfo<>();
        Map<String, Object > param = new HashMap<String, Object>();
//        param.put("datatype", datatype);
        StringBuilder sqlwhere = new StringBuilder();

        if ("querypc".equals(datatype)){
            // 批次查询
            String keyword = (String) super.getItem(pageInfo, "keyword");
            if (!Strings.isNullOrEmpty(keyword)) {
                String likestr = String.format("'%%%s%%'", keyword);
                sqlwhere.append(" (sqph like ").append(likestr)
                        .append(" OR sqdw like ").append(likestr)
                        .append(") ");
            }
        } else {
            String jynd = (String) super.getItem(pageInfo, "jynd");
            if (!Strings.isNullOrEmpty(jynd)) {
                sqlwhere.append("jynd='").append(jynd).append("'");
            }
        }

        if(sqlwhere.length() > 0) {
            param.put("sqlwhere", sqlwhere.toString());
        }
        param.put("sqllimit",
                String.format("%d,%d",(pageInfo.getPageSize() * (pageInfo.getPageNum()-1)),pageInfo.getPageSize()));
        if ("query".equals(datatype)){
            param.put("datatype", "query_cnt");
            List<Map<String, Object>> listcnt  = syService.callsyDatas(param);
            ret.setTotal((Long) listcnt.get(0).get("allcount"));
        } else if ("querypc".equals(datatype)){
            param.put("datatype", "querypc_cnt");
            List<Map<String, Object>> listcnt  = syService.callsyDatas(param);
            ret.setTotal((Long) listcnt.get(0).get("allcount"));
        }
        param.put("datatype", datatype);
        List<Map<String, Object>> list = syService.callsyDatas(param);
        ret.setList(list);
        return ResultGenerator.genSuccessResult(ret);
    }

    /**
     * 读取当前用户信息
     * @return
     */
    @PostMapping("/getuserinfor")
    public Result getuserinfor() {
        super.getUserAccount();
        FrameEmployee frameEmployee = frameEmployeeService.findBy("userid", _userid);
        return ResultGenerator.genSuccessResult(frameEmployee);
    }

    /**
     * 通过SQL方式查询兽药检验数据
     * @param pageInfo
     * @return
     */
    private Result getSyDataBySql(PageInfo<SyQuery> pageInfo){
        Map<String, Object> param = new HashMap<String, Object>();
        String keystr = "keyword";
        String keyword = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(keyword)) {
            param.put(keystr, keyword);
        }
        keystr = "jyph";
        String jyph = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(jyph)) {
            param.put(keystr, jyph);
        }
        keystr = "option";
        String option =   super.formatString(super.getItem(pageInfo, keystr));
        if (!Strings.isNullOrEmpty(option)) {
            param.put(keystr, option);
        }
        keystr = "currentstatus";
        String currentstatus = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(currentstatus)) {
            param.put(keystr, currentstatus);
        }
        keystr = "sfmy";
        String sfmy = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(sfmy)) {
            param.put(keystr, sfmy);
        }
        keystr = "sfjsy";
        String sfjsy = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(sfjsy)) {
            param.put(keystr, sfjsy);
        }
        keystr = "tjdx";
        String tjdx = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(tjdx)) {
            param.put(keystr, tjdx);
        }
        keystr = "jynd";
        String jynd = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(jynd)) {
            param.put(keystr, jynd);
        }
        // 当前操作流程节点
        String nodeindex = (String) super.getItem(pageInfo, "nodeindex");
        if (!Strings.isNullOrEmpty(nodeindex)) {
            param.put("nodeindex", nodeindex);
        }
        super.getUserAccount();
        if (ResourceUtil.JINYN_NO.equals(nodeindex)) {
            param.put("todoperson", _userid);
        }
        if (ResourceUtil.JINYN_REFER_NO.equals(nodeindex) || ResourceUtil.JINYN_CONFIRM_NO.equals(nodeindex)) {
            if (!ResourceUtil.BUSSINESS_UNIT_ID.equals(_user.getOrgId())){
                param.put("jybm", _user.getOrgId());
            }
        }

        keystr = "jybm";
        String jybm = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(jybm)) {
            param.put(keystr, jybm);
        }

        keystr = "startday";
        String startday = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(startday)) {
            param.put(keystr, startday);
        }
        keystr = "stopday";
        String stopday = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(stopday)) {
            param.put(keystr, stopday);
        }
        long cnt = syService.querySyDataCnt(param);
        param.put("startindex",(pageInfo.getPageNum()-1)* pageInfo.getPageSize());
        param.put("stopindex",pageInfo.getPageSize());
//        param.put("orderby", "w.jybh desc");
        List<Map<String, Object>> list = syService.querySyDatas(param);
        PageInfo<Map<String, Object>> ret = new PageInfo<>();
        ret.setList(list);
        ret.setTotal(cnt);
        return ResultGenerator.genSuccessResult(ret);
    }

}
