package com.forest.cl.web;

import com.alibaba.fastjson.JSONObject;
import com.aspose.cells.SaveFormat;
import com.forest.cl.model.ClFlowdata;
import com.forest.cl.model.ClQuery;
import com.forest.cl.model.ClWeituo;
import com.forest.cl.service.ClDetectitemService;
import com.forest.cl.service.ClFlowdataService;
import com.forest.cl.service.ClWeituoService;
import com.forest.core.BaseController;
import com.forest.core.BaseModel;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.utils.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/08/22.
 */
@RestController
@RequestMapping("/cl/weituo")
public class ClWeituoController extends BaseController {
  @Resource
  private ClWeituoService clWeituoService;
  @Resource
  private ClDetectitemService clDetectitemService;
  @Resource
  private ClFlowdataService clFlowdataService;

  @PostMapping("/delete")
  public Result delete(@RequestBody() ClWeituo clWeituo) {
    clWeituoService.deleteById(clWeituo.getId());
    return ResultGenerator.genSuccessResult();
  }

  @PostMapping("/save")
  public Result save(@RequestBody() ClWeituo clWeituo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    String message = "数据保存成功。";
    super.getUserAccount();
    clWeituo.setModifer(_userid);
    clWeituo.setModifytime(new Date());
    if (StringUtils.isEmpty(clWeituo.getId())) {
      clWeituo.setCljynd(super.getSyJynd());
      clWeituo.setJybh(super.getNewNumberofRemain(clWeituo.getJyxm()));
      if (Strings.isNullOrEmpty(clWeituo.getSydbh())) {
        clWeituo.setSydbh(clWeituo.getJybh());
      }
      clWeituo.setCreator(_userid);
      clWeituo.setCreatetime(new Date());
      clWeituoService.save(clWeituo);
      // 同步生成流程数据
      clWeituoService.initFlowData(clWeituo.getJybh());
      message = "新增数据成功。";
    } else {
      clWeituoService.update(clWeituo);
      message = "数据更新成功。";
    }
    return ResultGenerator.genSuccessResult(message);
  }

  @PostMapping("/detail")
  public Result detail(@RequestBody ClWeituo clWeituo) {
    clWeituo = clWeituoService.findById(clWeituo.getId());

    clWeituo.setJyxmmc(getJyxmName(clWeituo.getJyxm()));
    clWeituo.setRwlymc(ScsyResourceUtil.getDicitionary(clWeituo.getRwly()));
    clWeituo.setBfqkmc(ScsyResourceUtil.getDicitionary(clWeituo.getBfqk()));
    clWeituo.setBcqkmc(ScsyResourceUtil.getDicitionary(clWeituo.getBcqk()));
    clWeituo.setYsqkmc(ScsyResourceUtil.getDicitionary(clWeituo.getYsqk()));
//        String [] xms = clWeituo.getJyxm().replace("[","")
//            .replace("]","").replaceAll("\"", "").split(",");
//        StringBuilder name = new StringBuilder();
//        for(int i=0; i< xms.length; i++) {
//            if(name.length() > 0) {
//                name.append(" , ");
//            }
//            name.append(ScsyResourceUtil.getDicitionary(xms[i]));
//        }
//        clWeituo.setJyxmmc(name.toString());
    ClFlowdata clFlowdata = clFlowdataService.findBy("jybh", clWeituo.getJybh());
    if (clFlowdata != null) {
      clWeituo.setCurrentstatus(clFlowdata.getCurrentstatus());
    }
    return ResultGenerator.genSuccessResult(clWeituo);
  }

  @PostMapping("/list")
  public Result list(@RequestBody() @Valid PageInfo<ClWeituo> pageInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
    Condition condition = new Condition(ClWeituo.class);
    Example.Criteria criteria = condition.createCriteria();
    // 根据页面的查询条件编辑查询条件
    String keyword = super.formatString(super.getItem(pageInfo, "keyword"));
    if (!Strings.isNullOrEmpty(keyword)) {
      keyword = String.format("%%%s%%", keyword);
      criteria.orLike("jybh", keyword)
          .orLike("sydbh", keyword)
          .orLike("ypmc", keyword)
          .orLike("zsbh", keyword)
          .orLike("sjwmc", keyword)
          .orLike("wtkhw", keyword);
    }
    List<ClWeituo> list = clWeituoService.findByCondition(condition);
    List<String> jpbhlist = new ArrayList<String>();

    // 添加附加信息
//        List<ClDetectitem> xms =  clDetectitemService.findAll();
    Map<String, String> map = clDetectitemService.getMapdata();
//        xms.forEach(e->{map.put(e.getId(), e.getItemname());});
    list.forEach(e -> {
      e.setJyxmmc(map.get(e.getJyxm()));
      jpbhlist.add(e.getJybh());
    });

    Condition conditionflow = new Condition(ClFlowdata.class);
    Example.Criteria criteriaflow = conditionflow.createCriteria();
    criteriaflow.andIn("jybh", jpbhlist);
    List<ClFlowdata> flowdatas = clFlowdataService.findByCondition(conditionflow);
    Map<String, Integer> mapflow = new HashMap<String, Integer>();
    flowdatas.forEach(e -> {
      mapflow.put(e.getJybh(), e.getCurrentstatus());
    });
    list.forEach(e -> {
//            System.out.println(mapflow.get(e.getJybh()));
      e.setCurrentstatus(mapflow.get(e.getJybh()));
    });

    pageInfo.setTotal(page.getTotal());
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  @PostMapping("/listallinfor")
  public Result listallinfor(@RequestBody() @Valid PageInfo<ClQuery> pageInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
    Map<String, Object> param = new HashMap<String, Object>();
    String keyword = super.formatString(super.getItem(pageInfo, "keyword"));
    if (!Strings.isNullOrEmpty(keyword)) {
      param.put("keyword", keyword);
    }
    String keystr = "currentstatus";
    String currentstatus = (String) super.getItem(pageInfo, keystr);
    if (!Strings.isNullOrEmpty(currentstatus)) {
      param.put(keystr, currentstatus);
    }
    String cljynd = super.formatString(super.getItem(pageInfo, "cljynd"));
    if (!Strings.isNullOrEmpty(cljynd)) {
      param.put("cljynd", cljynd);
    }
    String startday = super.formatString(super.getItem(pageInfo, "startday"));
    if (!Strings.isNullOrEmpty(startday)) {
      param.put("startday", DateUtils.str2Timestamp(startday.concat(ConStant.MIN_TIME), DateUtils.datetimeFormat));
//        } else {
//            param.put("startday", DateUtils.str2Timestamp(ConStant.MIN_DATE.concat(ConStant.MIN_TIME),DateUtils.datetimeFormat));
    }
    String stopday = super.formatString(super.getItem(pageInfo, "stopday"));
    if (!Strings.isNullOrEmpty(stopday)) {
      param.put("stopday", DateUtils.str2Timestamp(stopday.concat(ConStant.MAX_TIME), DateUtils.datetimeFormat));
//        } else {
//            param.put("stopday", DateUtils.str2Timestamp(ConStant.MAX_DATE.concat(ConStant.MAX_TIME),DateUtils.datetimeFormat));
    }

    // 当前操作流程节点
    String nodeindex = (String) super.getItem(pageInfo, "nodeindex");
    super.getUserAccount();
    if ("5".equals(nodeindex)) {
      param.put("userid", _userid);
    }

//    String currentstatus = super.formatString(super.getItem(pageInfo, "currentstatus"));
//    if (!Strings.isNullOrEmpty(currentstatus)) {
//      param.put("currentstatus", Integer.parseInt(currentstatus));
//      if ("5".equals(currentstatus)) {
//        super.getUserAccount();
//        param.put("userid", _userid);
//      }
//    }

    param.put("orderby", "a.jybh desc");
    param.put("startindex", (pageInfo.getPageNum() - 1) * pageInfo.getPageSize());
    param.put("stopindex", pageInfo.getPageSize());
    long allcount = clWeituoService.queryRemainCounts(param);
    List<Map<String, Object>> list = clWeituoService.queryRemainInfors(param);

    list.forEach(d -> {
      d.put("jyxmmc", getJyxmName((String) d.get("jyxm")));
    });
    PageInfo<Map<String, Object>> pinf = new PageInfo<>();
    pinf.setTotal(allcount);
    pinf.setList(list);
    return ResultGenerator.genSuccessResult(pinf);
  }

  /**
   * 读取受检单位信息
   *
   * @param sjdw
   * @return
   */
  @PostMapping("/listsjdw")
  public Result listsjdw(@RequestParam(value = "sjdw") String sjdw) {
    List<ClWeituo> list = clWeituoService.getSjdw(sjdw);
    PageInfo<ClWeituo> pageInfo = new PageInfo<ClWeituo>();
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  @PostMapping("/listscdw")
  public Result listscdw(@RequestParam(value = "scdw") String scdw) {
    List<ClWeituo> list = clWeituoService.getScdw(scdw);
    PageInfo<ClWeituo> pageInfo = new PageInfo<ClWeituo>();
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  @PostMapping("/listcjyj")
  public Result listcjyj(@RequestParam(value = "cjyj") String cjyj) {
    List<String> list = clWeituoService.getSingleItem("cjyj", cjyj);
    PageInfo<String> pageInfo = new PageInfo<String>();
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  @PostMapping("/listjyyj")
  public Result listjyyj(@RequestParam(value = "jyyj") String jyyj) {
    List<String> list = clWeituoService.getSingleItem("jyyj", jyyj);
    PageInfo<String> pageInfo = new PageInfo<String>();
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }
  /**
   * 读取送样单位信息
   *
   * @param wtdw
   * @return
   */
  @PostMapping("/listwtdw")
  public Result listwtdw(@RequestParam(value = "wtdw") String wtdw) {
    List<ClWeituo> list = clWeituoService.getWtdw(wtdw);
    PageInfo<ClWeituo> pageInfo = new PageInfo<ClWeituo>();
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  /**
   * 打印报告
   *
   * @param canliuWeituo
   * @param request
   * @return
   */
  @PostMapping("/preview")
  public Result preview(@RequestBody() ClWeituo canliuWeituo, HttpServletRequest request) {
    canliuWeituo = clWeituoService.findById(canliuWeituo.getId());
    // 打印报告
    String filepath = this.clWeituoService.exportReport(canliuWeituo.getJybh());
    if (FileUtils.exists(ScsyReportUtil.getSystemRootPath() + filepath)) {
      request.setAttribute("filepath", filepath);
      return ResultGenerator.genSuccessResult(filepath);
    } else {
      return ResultGenerator.genFailResult("检验报告生成错误。");
    }
  }

  /**
   * 读取残留数据
   *
   * @param datatype
   * @param request
   * @return
   */
  @PostMapping("/getremaindatas")
  public Result getremaindatas(@RequestParam(value = "datatype") String datatype,
                               @RequestParam(value = "jynd") String jynd, HttpServletRequest request) {
//        String datatype = request.getParameter("datatype");
    if (StringUtils.isBlank(datatype)) {
      return ResultGenerator.genFailResult("请设置查询参数");
    }
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("datatype", datatype);
    if (Strings.isNullOrEmpty(jynd)) {
      param.put("sqlwhere", "");
    } else {
      param.put("sqlwhere", "cljynd='".concat(jynd).concat("'"));
    }
    List<Map<String, Object>> list = clWeituoService.getremaindatas(param);
    return ResultGenerator.genSuccessResult(list);
  }

  /**
   * 批量保存残留委托信息
   *
   * @param canliuWeituo 基础数据
   * @param request
   * @return
   */
  @PostMapping("/savemulti")
  public Result savemulti(@RequestBody() ClWeituo canliuWeituo, HttpServletRequest request) {
    super.getUserAccount();
    String  message = String.format("%d条残留委托信息添加成功", canliuWeituo.getCount());
    canliuWeituo.setCljynd(super.getSyJynd()); // ScsyResourceUtil.currentYear);

    String sydbhPre = getSydbhPrefix(canliuWeituo.getSydbh());
    String ypbhpre = getypbhPrefix(canliuWeituo.getYpbh());
    //  获取起始编号（样品编号、送样单编号)
    Integer sydbh_start = getLastNewSydbh(canliuWeituo);
    Integer ypbh_start = getNewYpbh(canliuWeituo);
    int index = 0;
    while (index < canliuWeituo.getCount()) {
      ClWeituo wt = new ClWeituo();
      BeanUtils.copyProperties(canliuWeituo, wt);
      wt.setId(null);
      wt.setJybh(super.getNewNumberofRemain(wt.getJyxm()));
      wt.setSydbh(sydbhPre.concat(StringUtil.alignLeft(sydbh_start.toString(), 2, "0")));
      wt.setYpbh(ypbhpre.concat(StringUtil.alignLeft(ypbh_start.toString(), 2, "0")));
/*
      if (Strings.isNullOrEmpty(wt.getSydbh())) {
        wt.setSydbh(wt.getJybh());
      }
      if (Strings.isNullOrEmpty(wt.getYpbh())) {
        wt.setYpbh(wt.getJybh());
      }
*/
      wt.setModifer(_userid);
      wt.setModifytime(new Date());
      wt.setCreator(_userid);
      wt.setCreatetime(new Date());
      clWeituoService.save(wt);
      clWeituoService.initFlowData(wt.getJybh());
      index++;
      sydbh_start++;
      ypbh_start++;
    }
    return ResultGenerator.genSuccessResult(message);
  }

  /**
   * 保存流程数据
   *
   * @param request
   * @return
   */
  @PostMapping("/saveflowlog")
  public Result saveflowlog(HttpServletRequest request) {
    String message = null;
    // 是否退回操作
    Boolean isback = false;
    if (StringUtils.isNotBlank(request.getParameter("isback"))) {
      isback = Boolean.valueOf(request.getParameter("isback"));
    }
    // 当前节点
    Integer node = Integer.valueOf(request.getParameter("node"));
    // 待处理的检验委托编号
    String bhlist = request.getParameter("bhlist");
    // 待办人（检验员）
    String todouser = request.getParameter("nextuser");

    if (isback) {
      message = "退回处理成功。";
    } else {
      message = "提交成功。";
    }
    Map<String, Object> params = new HashMap<String, Object>();
    params.put(ClFlowdata.KEYS_ISBACK, isback);
    params.put(ClFlowdata.KEYS_CURRENTNODE, node);
    // 提交/退回节点
    if (StringUtils.isNotBlank(request.getParameter("nextNode"))) {
      Integer nextNode = Integer.valueOf(request.getParameter("nextNode"));
      params.put(ClFlowdata.KEYS_NEXTNODE, nextNode);
    }
    if (StringUtils.isNotBlank(todouser)) {
      params.put(ClFlowdata.KEYS_NEXTUSER, todouser);
    }
    params.put(ClFlowdata.KEYS_JYBH, bhlist);
    params.put(ClFlowdata.KEYS_REMARK, message);
    super.getUserAccount();
    params.put(ClFlowdata.KEYS_USER, _user);
    if (StringUtils.isNotBlank(request.getParameter("ypcll"))) {
      params.put(ClFlowdata.KEYS_REMAIN, request.getParameter("ypcll"));
    }

    // 执行提交/退回处理，更新当前进度情况
    clWeituoService.submitProcess(params);

    return ResultGenerator.genSuccessResult(message);
  }

  /**
   * Web打印请求处理（打印文档预览显示）
   *
   * @param request
   * @return
   */
  @PostMapping("/printreport")
  public Result printreport(HttpServletRequest request) {
    String bhs = request.getParameter("jybhs");
    // 打印报告
    long start = System.currentTimeMillis();
    String filepath = this.clWeituoService.exportReport(bhs);
    System.out.println("打印耗时：" + (System.currentTimeMillis() - start) + "毫秒");
    return ResultGenerator.genSuccessResult(filepath);
  }

  @PostMapping("/exportexcel")
  public Result exportexcel(HttpServletRequest request) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sqlwhere", "cljynd='".concat(super.getSyJynd()).concat("'"));
    List<Map<String, Object>> list = this.clWeituoService.queryList(param);
    param.put("datas", new HashMapDataTable(list));
    String templateFile = String.format("%s%sexcel/残留汇总表.xltx",
        ScsyReportOfficeUtil.getSystemRootPath(), ScsyReportOfficeUtil.getTemplatePath());
    String targetFile = ScsyReportOfficeUtil.getRealReportPath().concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx");
    String filepath = AsposeCellUtil.replaceText(templateFile, targetFile, param, SaveFormat.XLSX);
//        if(FileUtils.exists(ScsyReportUtil.getSystemRootPath() + filepath)) {
//            request.setAttribute("filepath", filepath);
//            return ResultGenerator.genSuccessResult(filepath);
//        }
    return ResultGenerator.genSuccessResult(filepath.replaceFirst(ScsyReportOfficeUtil.getSystemRootPath(), ""));
  }

  @PostMapping("/queryexcel")
  public Result queryexcel(HttpServletRequest request) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sqlwhere", "cljynd='".concat(super.getSyJynd()).concat("'"));
    List<Map<String, Object>> list = this.clWeituoService.queryList(param);
    return ResultGenerator.genSuccessResult(list);
  }


  /**
   * 导出检验报告PDF
   *
   * @param request
   * @param response
   */
  @PostMapping("/exportpdf")
  public void exportpdf(HttpServletRequest request, HttpServletResponse response) {
    String jybhs = request.getParameter("jybhs");
    String[] jybhlist = jybhs.split(",");
    // 生成新的目录
    File folder = FileUtils.getFolder(ScsyReportUtil.getRealReportPath() + UUID.randomUUID().toString());
    try {
      // 复制PDF文件
      for (String jybh : jybhlist) {
        File f = new File(ScsyReportUtil.getRealArchiveReportPath() + jybh + ScsyReportUtil.PDF_SUFFIX);
        if (!f.exists()) {
          f = new File(ScsyReportUtil.getRealReportPath() + jybh + ScsyReportUtil.PDF_SUFFIX);
        }
        if (f.exists()) {
          FileUtils.copyFile(f.getPath(), folder.getPath() + "/" + f.getName());
        }
      }
      // 打包zip文件
      String ls_filename = FileUtils.zip("残留检验报告.zip", "", folder.getPath()); // 压缩文件
      // 文件下载
      File file = new File(ls_filename);
      String filename = file.getName();
      InputStream fis = new BufferedInputStream(new FileInputStream(ls_filename));
      // 清空response
      response.reset();
      // 设置response的Header
      response.setContentType("text/html;charset=utf-8");
      request.setCharacterEncoding("UTF-8");
      response.addHeader("Content-Length", "" + file.length());
      OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
      response.setContentType("application/octet-stream");
      response.setHeader("Content-disposition", "attachment;filename="
          + new String(filename.getBytes("utf-8"), "ISO8859-1"));
      int bytesRead = 0;
      byte[] buffer = new byte[8192];
      while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
        toClient.write(buffer, 0, bytesRead);
      }
      toClient.write(buffer);
      toClient.flush();
      toClient.close();
      fis.close();
      // 删除打包前文件
      FileUtils.deleteFolder(folder.getPath());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void addNewWeituo(ClWeituo weituo) {
    ClWeituo clWeituo = new ClWeituo();
    BeanUtils.copyProperties(weituo, clWeituo);
    String sydbh = "";
    clWeituo.setSydbh(sydbh);
    clWeituoService.save(clWeituo);
  }

  /**
   * 根据检验项目查询 检验项目名称
   *
   * @param jyxm
   * @return
   */
  private String getJyxmName(String jyxm) {
    List<String> names = new ArrayList<>();
    String[] xms = jyxm.replace("[", "")
        .replace("]", "").replaceAll("\"", "").split(",");
    Arrays.asList(xms).forEach(x -> {
      names.add(ScsyResourceUtil.getDicitionary(x));
    });
    return names.toString();
  }

  private String getSydbhPrefix(String sydbh)
  {
    return sydbh.substring(0, sydbh.lastIndexOf("/") + 1);
  }
  private Integer getLastNewSydbh(ClWeituo weituo) {
    Page page = PageHelper.startPage(1, 10, true);
    String prefix = getSydbhPrefix(weituo.getSydbh());
    Integer inputNo = StringUtil.toInt(weituo.getSydbh().replace(prefix, ""));
    Condition condition = new Condition(ClWeituo.class);
    Example.Criteria criteria = condition.createCriteria();
    String keyword = String.format("%s%%", prefix);
    criteria.orLike("sydbh", keyword);
    condition.orderBy("sydbh").desc();
    List<ClWeituo> list = clWeituoService.findByCondition(condition);
    Integer index = 0;
    if(list.size() > 0) {
      index = Integer.valueOf(list.get(0).getSydbh().replace(prefix,""));
    }
    if (index++ < inputNo) {
      index = inputNo;
    }
    return index;
  }

  private String getypbhPrefix(String ypbh)
  {
    return ypbh.substring(0, ypbh.lastIndexOf("-") + 1);
  }
  private Integer getNewYpbh(ClWeituo weituo) {
    Page page = PageHelper.startPage(1, 10, true);
    String prefix = getypbhPrefix(weituo.getYpbh());
    Integer inputNo = StringUtil.toInt(weituo.getYpbh().replace(prefix, ""));
    Condition condition = new Condition(ClWeituo.class);
    Example.Criteria criteria = condition.createCriteria();
    String keyword = String.format("%s%%", prefix);
    criteria.orLike("ypbh", keyword);
    condition.orderBy("ypbh").desc();
    List<ClWeituo> list = clWeituoService.findByCondition(condition);
    Integer index = 0;
    if(list.size() > 0) {
      index = Integer.valueOf(list.get(0).getYpbh().replace(prefix,""));
    }
    if (index++ < inputNo) {
      index = inputNo;
    }
    return index;
  }
}
