package com.forest.sy.web;

import com.alibaba.druid.sql.visitor.functions.If;
import com.forest.core.BaseController;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.FrameDictionary;
import com.forest.project.service.FrameDictionaryService;
import com.forest.project.service.FrameOrganizationService;
import com.forest.sy.model.SyApplyWt;
import com.forest.sy.model.SyFlowdata;
import com.forest.sy.model.SyWeituo;
import com.forest.sy.service.SyApplyWtService;
import com.forest.sy.service.SyJymdService;
import com.forest.sy.service.SyService;
import com.forest.sy.service.SyWeituoService;
import com.forest.utils.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
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
 * Created by CodeGenerator on 2018/09/10.
 */
@RestController
@RequestMapping("/sy/weituo")
public class SyWeituoController extends BaseController {
  @Resource
  private SyWeituoService syWeituoService;
  @Resource
  private SyApplyWtService syApplyWtService;
  //    @Resource
//    private SyFlowdataService syFlowdataService;
  @Resource
  private FrameDictionaryService frameDictionaryService;
  @Resource
  private FrameOrganizationService frameOrganizationService;
  @Resource
  private SyJymdService syJymdService;
  @Resource
  private SyService syService;

  @PostMapping("/delete")
  public Result delete(@RequestBody() SyWeituo syWeituo) {
    syWeituoService.deleteById(syWeituo.getId());
    return ResultGenerator.genSuccessResult();
  }

  @PostMapping("/save")
  public Result save(@RequestBody() SyWeituo syWeituo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    String message = "数据保存成功。";
    super.getUserAccount();
    syWeituo.setModifer(_userid);
    syWeituo.setModifytime(new Date());
    if (StringUtils.isEmpty(syWeituo.getId())) {
      if (!Strings.isNullOrEmpty(syWeituo.getYpbh()) &&
          syWeituoService.findBy("ypbh", syWeituo.getYpbh()) != null){
        message = "数据保存失败：样品编号"+ syWeituo.getYpbh() +"不能重复！";
        return ResultGenerator.genFailResult(message);
      }

      syWeituo.setJynd(super.getSyJynd());
      if(Strings.isNullOrEmpty(syWeituo.getYpbh())) {
        syWeituo.setJybh(super.getNewNumberofDrugs(syWeituo.getJymd()));
        syWeituo.setYpbh(syWeituo.getJybh());
      }
      syWeituo.setCreator(_userid);
      syWeituo.setCreatetime(new Date());
      syWeituoService.save(syWeituo);
      SyApplyWt wt = new SyApplyWt();
      wt.setSqph(syWeituo.getSqph());
      wt.setYpbh(syWeituo.getYpbh());
      if (syApplyWtService.findBy("ypbh", wt.getYpbh()) == null) {
        syApplyWtService.save(wt);
      }
      if(!Strings.isNullOrEmpty(syWeituo.getJybh())) {
        // 同步生成流程数据
        syWeituoService.initFlowData(syWeituo.getJybh(),1);
      }

      message = "新增数据成功。";
    } else {
      syWeituoService.update(syWeituo);
      message = "数据更新成功。";
    }
    return ResultGenerator.genSuccessResult(message);
  }

  @PostMapping("/detail")
  public Result detail(@RequestBody SyWeituo syWeituo) {
    if (!Strings.isNullOrEmpty(syWeituo.getYpbh())) {
      syWeituo = syWeituoService.findBy("ypbh", syWeituo.getYpbh());
    } else if (!Strings.isNullOrEmpty(syWeituo.getJybh())) {
      syWeituo = syWeituoService.findBy("jybh", syWeituo.getJybh());
    } else {
      syWeituo = syWeituoService.findById(syWeituo.getId());
    }

    syWeituo.setJyxmmc(getDictionaryName(syWeituo.getJyxm()));
    syWeituo.setCjhjmc(getDictionaryName(syWeituo.getCjhj()));
    syWeituo.setCjlxmc(getDictionaryName(syWeituo.getCjlx()));
    syWeituo.setCjyjmc(getDictionaryName(syWeituo.getCjyj()));
    syWeituo.setYplxmc(getDictionaryName(syWeituo.getYplx()));
    syWeituo.setBzmc(getDictionaryName(syWeituo.getBz()));
    syWeituo.setJxmc(getDictionaryName(syWeituo.getJx()));
    syWeituo.setQyfsmc(getDictionaryName(syWeituo.getQyfs()));
    if (!Strings.isNullOrEmpty(syWeituo.getJybm())) {
      syWeituo.setJybmmc(frameOrganizationService.findById(syWeituo.getJybm()).getName());
    }
    if (!Strings.isNullOrEmpty(syWeituo.getJymd())) {
      syWeituo.setJymdmc(syJymdService.findById(syWeituo.getJymd()).getJymdName());
    }

    return ResultGenerator.genSuccessResult(syWeituo);
  }

  @PostMapping("/list")
  public Result list(@RequestBody() @Valid PageInfo<SyWeituo> pageInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
    Condition condition = new Condition(SyWeituo.class);
    Example.Criteria criteria = condition.createCriteria();
    // 根据页面的查询条件编辑查询条件
    String jybm = (String) super.getItem(pageInfo, "jybm");
    if (!Strings.isNullOrEmpty(jybm)) {
      criteria.andEqualTo("jybm", jybm);
    }
    criteria.andNotEqualTo("jybh", "");
    String keyword = (String) super.getItem(pageInfo, "ypmc");
    if (!Strings.isNullOrEmpty(keyword)) {
      criteria.andLike("ypmc", String.format("%%s%s%%s",keyword));
    }
    List<SyWeituo> list = syWeituoService.findByCondition(condition);
    list.forEach(syWeituo -> {
      syWeituo.setJyxmmc(getDictionaryName(syWeituo.getJyxm()));
      syWeituo.setCjhjmc(getDictionaryName(syWeituo.getCjhj()));
      syWeituo.setCjlxmc(getDictionaryName(syWeituo.getCjlx()));
      syWeituo.setCjyjmc(getDictionaryName(syWeituo.getCjyj()));
      syWeituo.setYplxmc(getDictionaryName(syWeituo.getYplx()));
      syWeituo.setBzmc(getDictionaryName(syWeituo.getBz()));
      syWeituo.setJxmc(getDictionaryName(syWeituo.getJx()));
      syWeituo.setQyfsmc(getDictionaryName(syWeituo.getQyfs()));
      if (!Strings.isNullOrEmpty(syWeituo.getJybm())) {
        syWeituo.setJybmmc(frameOrganizationService.findById(syWeituo.getJybm()).getName());
      }
      if (!Strings.isNullOrEmpty(syWeituo.getJymd())) {
        syWeituo.setJymdmc(syJymdService.findById(syWeituo.getJymd()).getJymdName());
      }
    });
    pageInfo.setTotal(page.getTotal());
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  @PostMapping("/listhistory")
  public Result listhistory(@RequestBody() @Valid PageInfo<SyWeituo> pageInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    Map<String, Object> param = new HashMap<String, Object>();
    String keystr = "ypmc";
    String keyword = (String) super.getItem(pageInfo, keystr);
    if (!Strings.isNullOrEmpty(keyword)) {
      param.put("keyword", keyword);
    }

    keystr = "jybm";
    String jybm = (String) super.getItem(pageInfo, keystr);
    if (!Strings.isNullOrEmpty(jybm)) {
      param.put(keystr, jybm);
    }

    long cnt = syWeituoService.gethistoryDataCnt(param);
    param.put("startindex",(pageInfo.getPageNum()-1)* pageInfo.getPageSize());
    param.put("stopindex",pageInfo.getPageSize());
//        param.put("orderby", "w.jybh desc");
    List<SyWeituo> list = syWeituoService.gethistoryDatas(param);
    list.forEach(syWeituo ->{
      syWeituo.setJyxmmc(getDictionaryName(syWeituo.getJyxm()));
      syWeituo.setCjhjmc(getDictionaryName(syWeituo.getCjhj()));
      syWeituo.setCjlxmc(getDictionaryName(syWeituo.getCjlx()));
      syWeituo.setCjyjmc(getDictionaryName(syWeituo.getCjyj()));
      syWeituo.setYplxmc(getDictionaryName(syWeituo.getYplx()));
      syWeituo.setBzmc(getDictionaryName(syWeituo.getBz()));
      syWeituo.setJxmc(getDictionaryName(syWeituo.getJx()));
      syWeituo.setQyfsmc(getDictionaryName(syWeituo.getQyfs()));
    });

    PageInfo<SyWeituo> ret = new PageInfo<>();
    ret.setList(list);
    ret.setTotal(cnt);
    return ResultGenerator.genSuccessResult(ret);
  }

  /**
   * 收件处理
   *
   * @param syWeituo
   * @param bindingResult
   * @return
   */
  @PostMapping("/shoujian")
  public Result shoujian(@RequestBody() SyWeituo syWeituo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    String message = "收件处理完成。";
    syWeituo = syWeituoService.findBy("ypbh", syWeituo.getYpbh());
    super.getUserAccount();
    syWeituo.setModifer(_userid);
    syWeituo.setModifytime(DateUtils.getDate());
//    syWeituo.setSjrq(DateUtils.getDate());
    if (Strings.isNullOrEmpty(syWeituo.getJybh())) {
      syWeituo.setJybh(super.getNewNumberofDrugs(syWeituo.getJymd()));
      syWeituoService.update(syWeituo);
      // 同步生成流程数据
      syWeituoService.initFlowData(syWeituo.getJybh(), 2);
    }
    return ResultGenerator.genSuccessResult(message);
  }

  @PostMapping("/listbysqph")
  public Result listbysqph(@RequestBody() @Valid PageInfo<SyApplyWt> pageInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
    Condition condition = new Condition(SyApplyWt.class);
    Example.Criteria criteria = condition.createCriteria();
    // 根据页面的查询条件编辑查询条件
    criteria.andEqualTo("sqph", pageInfo.getList().get(0).getSqph());
    condition.orderBy("ypbh").desc();
    List<SyApplyWt> sywtlist = syApplyWtService.findByCondition(condition);

    List<SyWeituo> list = new ArrayList<SyWeituo>();
    sywtlist.forEach(e -> {
      SyWeituo syWeituo = syWeituoService.findBy("ypbh", e.getYpbh());
      syWeituo.setJyxmmc(ScsyResourceUtil.getDicitionary(syWeituo.getJyxm()));
      syWeituo.setCjhjmc(ScsyResourceUtil.getDicitionary(syWeituo.getCjhj()));
      syWeituo.setCjlxmc(ScsyResourceUtil.getDicitionary(syWeituo.getCjlx()));
      syWeituo.setCjyjmc(ScsyResourceUtil.getDicitionary(syWeituo.getCjyj()));
      syWeituo.setYplxmc(ScsyResourceUtil.getDicitionary(syWeituo.getYplx()));
      syWeituo.setBzmc(ScsyResourceUtil.getDicitionary(syWeituo.getBz()));
      syWeituo.setJxmc(ScsyResourceUtil.getDicitionary(syWeituo.getJx()));
      syWeituo.setQyfsmc(ScsyResourceUtil.getDicitionary(syWeituo.getQyfs()));
      syWeituo.setJybmmc(ScsyResourceUtil.getOrganization(syWeituo.getJybm()));
      syWeituo.setJymdmc(ScsyResourceUtil.getCheckTargets(syWeituo.getJymd()));
      list.add(syWeituo);
    });
    PageInfo<SyWeituo> pageInfoRet = new PageInfo<SyWeituo>();
    pageInfoRet.setTotal(page.getTotal());
    pageInfoRet.setList(list);
    return ResultGenerator.genSuccessResult(pageInfoRet);
  }

  @PostMapping("/listbyapply")
  public Result listbyapply(@RequestBody() @Valid PageInfo<SyWeituo> pageInfo) {
    PageInfo<Map<String, Object>> ret = new PageInfo<>();
    Map<String, Object> param = new HashMap<String, Object>();
    super.getUserAccount();
    StringBuilder sqlwhere = new StringBuilder();
    String sqph = (String) super.getItem(pageInfo, "sqph");
    if (!Strings.isNullOrEmpty(sqph)) {
      sqlwhere.append(" sqph = '").append(sqph).append("' ");
    } else {
      sqlwhere.append(" creator = '").append(_userid).append("' ");
    }
    String keyword = (String) super.getItem(pageInfo, "ypmc");
    if (!Strings.isNullOrEmpty(keyword)) {
      if (sqlwhere.length() > 0) {
        sqlwhere.append(" and ");
      }
      sqlwhere.append(" ypmc + spmc like '%").append(keyword).append("%'");
    }
    param.put("sqlwhere", sqlwhere.toString());
    param.put("sqllimit", String.format("%d,%d", pageInfo.getStartRow(), pageInfo.getPageSize()));
    param.put("datatype", "queryapply_cnt");
    List<Map<String, Object>> listcnt = syService.callsyDatas(param);
    ret.setTotal((Long) listcnt.get(0).get("allcount"));
    param.put("datatype", "queryapply");
    List<Map<String, Object>> list = syService.callsyDatas(param);
    ret.setList(list);
    return ResultGenerator.genSuccessResult(ret);
  }

  /**
   * 读取生产单位信息
   *
   * @param scdw
   * @return
   */
  @PostMapping("/listscdw")
  public Result listscdw(@RequestParam(value = "scdw") String scdw) {
    List<String> list = syService.getSySingleItem("scdw", scdw);
    PageInfo<String> pageInfo = new PageInfo<String>();
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  @PostMapping("/listcjdw")
  public Result listcjdw(@RequestParam(value = "cjdw") String cjdw) {
    List<String> list = syService.getSySingleItem("cjdw", cjdw);
    PageInfo<String> pageInfo = new PageInfo<String>();
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  @PostMapping("/listsydw")
  public Result listsydw(@RequestParam(value = "sydw") String sydw) {
    List<String> list = syService.getSySingleItem("sydw", sydw);
    PageInfo<String> pageInfo = new PageInfo<String>();
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  /**
   * 读取委托单位信息
   *
   * @param wtdw
   * @return
   */
  @PostMapping("/listwtdw")
  public Result listwtdw(@RequestParam(value = "wtdw") String wtdw) {
    List<SyWeituo> list = syWeituoService.getWtdw(wtdw);
    PageInfo<SyWeituo> pageInfo = new PageInfo<SyWeituo>();
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
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
    params.put(SyFlowdata.KEYS_ISBACK, isback);
    params.put(SyFlowdata.KEYS_CURRENTNODE, node);
    // 提交/退回节点
    if (StringUtils.isNotBlank(request.getParameter("nextNode"))) {
      Integer nextNode = Integer.valueOf(request.getParameter("nextNode"));
      params.put(SyFlowdata.KEYS_NEXTNODE, nextNode);
    }
    if (StringUtils.isNotBlank(todouser)) {
      params.put(SyFlowdata.KEYS_NEXTUSER, todouser);
    }
    params.put(SyFlowdata.KEYS_JYBH, bhlist);
    params.put(SyFlowdata.KEYS_REMARK, message);
    super.getUserAccount();
    params.put(SyFlowdata.KEYS_USER, _user.getName().concat("(").concat(_userid).concat(")"));
    if (StringUtils.isNotBlank(request.getParameter("ypcll"))) {
      params.put(SyFlowdata.KEYS_REMAIN, request.getParameter("ypcll"));
    }

    // 收件处理时，更新收件时间
    if (!isback) {
      SyWeituo weituo = new SyWeituo();
      weituo.setSjrq(DateUtils.getDate());
      weituo.setModifytime(DateUtils.getDate());
      Condition condition = new Condition(SyWeituo.class);
      Example.Criteria criteria = condition.createCriteria();
      criteria.andIn("jybh", Arrays.asList(bhlist.split(",")));
      syWeituoService.updateByCondition(weituo, condition);
    }

    // 执行提交/退回处理，更新当前进度情况
    syWeituoService.submitProcess(params);

    return ResultGenerator.genSuccessResult(message);
  }

  /**
   * 打印报告
   *
   * @param weituo
   * @param request
   * @return
   */
  @PostMapping("/preview")
  public Result preview(@RequestBody() SyWeituo weituo, HttpServletRequest request) {
    weituo = syWeituoService.findById(weituo.getId());
    String filepath = super.getGuidangFileofDrugs(weituo.getJybh());
    if (filepath == null) {
      // 打印报告
      filepath = this.syWeituoService.exportReport(weituo.getJybh());
    }
    if (FileUtils.exists(ScsyReportUtil.getSystemRootPath() + filepath)) {
      request.setAttribute("filepath", filepath);
      return ResultGenerator.genSuccessResult(filepath);
    } else {
      return ResultGenerator.genFailResult("检验报告生成错误。");
    }
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
//        long start = System.currentTimeMillis();
    List<String> fileNameList = new ArrayList<>();
    Arrays.asList(bhs.split(",")).forEach(bh -> {
      String fl = super.getGuidangFileofDrugs(bh);
      if (fl == null) {
        fl = syWeituoService.exportReport(bh);
      }
      fileNameList.add(fl);
    });

    String filepath = ScsyReportUtil.mergePdfFiles(fileNameList, ScsyReportUtil.SY_SUBDICTIONARY); //this.syWeituoService.exportReport(bhs);
//        System.out.println("打印耗时："+(System.currentTimeMillis()-start) + "毫秒");
    return ResultGenerator.genSuccessResult(filepath);
  }

  /**
   * 导出检验报告PDF(批量打包)
   *
   * @param request
   * @param response
   */
  @PostMapping("/exportpdf")
  public Result exportpdf(HttpServletRequest request, HttpServletResponse response) {
    String jybhs = request.getParameter("jybhs");
    String[] jybhlist = jybhs.split(",");
    List<String> filenames = new ArrayList<String>();
//        // 生成新的目录
//        File folder = FileUtils.getFolder(ScsyReportUtil.getRealReportPath() +UUID.randomUUID().toString());
//        try{
//            String subDictionary  = ScsyReportUtil.SY_SUBDICTIONARY;
    // 复制PDF文件
    for (String jybh : jybhlist) {
      String fn = super.getGuidangFileofDrugs(jybh);
      if (Strings.isNullOrEmpty(fn)) {
        fn = syWeituoService.exportReport(jybh);
      }
      fn = fn.startsWith("/") ? fn.substring(1) : fn;
      filenames.add(ScsyReportUtil.getSystemRootPath().concat(fn));
//                FileUtils.copyFile(ScsyReportOfficeUtil.getRealReportPath().concat(fn), folder.getPath().concat("/").concat(jybh).concat(ScsyReportUtil.PDF_SUFFIX));
//                File f = new File(ScsyReportUtil.getRealArchiveReportPath().concat(subDictionary)
//                        .concat(jybh).concat(ScsyReportUtil.PDF_SUFFIX));
//                if(!f.exists()){
//                    f = new File(ScsyReportOfficeUtil.getRealReportPath()
//                            .concat(subDictionary).concat(jybh).concat(ScsyReportUtil.PDF_SUFFIX));
//                }
//                if(!f.exists()){
//                     f= new File(ScsyReportOfficeUtil.getRealReportPath().concat(syWeituoService.exportReport(jybh)));
//                }
//                if(f.exists()){
//                    FileUtils.copyFile(f.getPath(), folder.getPath()+"/" + f.getName());
//                }
    }
    String targetFile = ScsyReportUtil.getRealReportPath().concat(ScsyReportUtil.SY_SUBDICTIONARY)
        .concat("兽药检验报告").concat(DateUtils.formatYMDHMS()).concat(".zip");
    ZIPUtil.compress(filenames, targetFile);
    return ResultGenerator.genSuccessResult(ScsyReportUtil.path2Url(targetFile));
//            // 打包zip文件
//            String ls_filename = FileUtils.zip(ScsyReportOfficeUtil.getRealReportPath().concat("兽药检验报告.zip"), "", folder.getPath()); // 压缩文件
//            // 删除打包前文件
//            FileUtils.deleteFolder(folder.getPath());
            /*// 文件下载
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
            response.setHeader("Content-disposition", "attachment;filename= "
+                    java.net.URLEncoder.encode(filename, "UTF-8"));
//                    + new String(filename.getBytes("utf-8"), "ISO8859-1"));
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                toClient.write(buffer, 0, bytesRead);
            }
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            fis.close();*/
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
  }

  /**
   * 生成委托合同PDF
   *
   * @param request
   */
  @PostMapping("/printht")
  public Result printht(@RequestBody() SyWeituo weituo, HttpServletRequest request) {
    String filepath;
    if ("297eb7aa582fb5cc01582fb72c0a0001".equals(weituo.getJymd())) {
      filepath = this.syWeituoService.exportRefer(weituo.getYpbh());
    } else {
      filepath = this.syWeituoService.exportContract(weituo.getYpbh());
    }
//        String filepath = this.syWeituoService.exportContract(weituo.getYpbh());
    return ResultGenerator.genSuccessResult(filepath);
  }

  private String getDictionaryName(String id) {
    if (!Strings.isNullOrEmpty(id)) {
      FrameDictionary frameDictionary = frameDictionaryService.findById(id);
      return frameDictionary != null ? frameDictionary.getName() : null;
    } else {
      return null;
    }


  }

}
