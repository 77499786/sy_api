package com.forest.sy.web;

import com.forest.core.BaseController;
import com.forest.core.ProjectConstant;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.FrameEmployee;
import com.forest.sy.model.SyFlowdata;
import com.forest.sy.model.SyResult;
import com.forest.sy.model.SyWeituo;
import com.forest.sy.service.SyFlowdataService;
import com.forest.sy.service.SyResultService;
import com.forest.sy.service.SyWeituoService;
import com.forest.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/09/10.
 */
@RestController
@RequestMapping("/sy/flowdata")
public class SyFlowdataController extends BaseController {
  @Resource
  private SyFlowdataService syFlowdataService;
  @Resource
  private SyResultService syResultService;
  @Resource
  private SyWeituoService syWeituoService;

  @PostMapping("/delete")
  public Result delete(@RequestBody() SyFlowdata syFlowdata) {
    syFlowdataService.deleteById(syFlowdata.getId());
    return ResultGenerator.genSuccessResult();
  }

  @PostMapping("/save")
  public Result save(@RequestBody() SyFlowdata syFlowdata, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    String message = "数据保存成功。";
    super.getUserAccount();
    syFlowdata.setModifer(_userid);
    syFlowdata.setModifytime(new Date());
    if (StringUtils.isEmpty(syFlowdata.getId())) {
      syFlowdata.setCreator(_userid);
      syFlowdata.setCreatetime(new Date());
      syFlowdataService.save(syFlowdata);
      message = "新增数据成功。";
    } else {
      syFlowdataService.update(syFlowdata);
      message = "数据更新成功。";
    }
    return ResultGenerator.genSuccessResult(message);
  }

  @PostMapping("/savewt")
  public Result savewt(@RequestBody() SyFlowdata syFlowdata, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    String message = "原始委托保存成功。";
    SyFlowdata flowdata = syFlowdataService.findBy("jybh", syFlowdata.getJybh());
    flowdata.setYswt(syFlowdata.getYswt());
    super.getUserAccount();
    flowdata.setModifer(_userid);
    flowdata.setModifytime(new Date());
    syFlowdataService.update(flowdata);
    return ResultGenerator.genSuccessResult(message);
  }

  /**
   * 报告发放数据保存
   *
   * @param syFlowdata
   * @param bindingResult
   * @return
   */
  @PostMapping("/savesend")
  public Result savesend(@RequestBody() SyFlowdata syFlowdata, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    String message = "数据保存成功。";
    super.getUserAccount();
    SyFlowdata entity = syFlowdataService.findBy("jybh", syFlowdata.getJybh());
    entity.setSendto(syFlowdata.getSendto());
    entity.setPostday(syFlowdata.getPostday());
    entity.setExpress(syFlowdata.getExpress());
    syFlowdata.setModifer(_userid);
    syFlowdata.setModifytime(new Date());
    syFlowdataService.update(syFlowdata);
    message = "数据更新成功。";
    return ResultGenerator.genSuccessResult(message);
  }

  /**
   * 兽药/假兽药判定处理
   *
   * @param syFlowdata
   * @param bindingResult
   * @return
   */
  @PostMapping("/savefake")
  public Result savefake(@RequestBody() SyFlowdata syFlowdata, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    String message = "兽药判定完成。";
    super.getUserAccount();
    SyFlowdata entity = syFlowdataService.findBy("jybh", syFlowdata.getJybh());
    entity.setSfjsy(syFlowdata.getSfjsy());
    entity.setJsyjl(syFlowdata.getJsyjl());
    syFlowdata.setModifer(_userid);
    syFlowdata.setModifytime(new Date());
    syFlowdataService.update(syFlowdata);
    return ResultGenerator.genSuccessResult(message);
  }

  /**
   * 分配检验人员数据保存
   *
   * @return
   */
  @PostMapping("/savefenpei")
  public Result savefenpei(HttpServletRequest request) {
    String message = "数据保存成功。";
    super.getUserAccount();
    List<String> bhs = Arrays.asList(request.getParameter("jybhlist").split(","));
    String jyr = request.getParameter("jyry");
    FrameEmployee user = frameEmployeeService.findBy("userid", jyr);
    bhs.forEach(jybh -> {
      SyFlowdata entity = syFlowdataService.findBy("jybh", jybh);
      entity.setTodoperson(jyr);
      entity.setJyrxm(user.getName());
      entity.setModifer(_userid);
      entity.setModifytime(new Date());
      syFlowdataService.update(entity);
    });
    return ResultGenerator.genSuccessResult(message);
  }

  @PostMapping("/saveresult")
  public Result saveresult(@RequestBody() SyFlowdata syFlowdata, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    String message = "数据保存成功。";
    super.getUserAccount();

    if(!syFlowdata.getResults().isEmpty() && syFlowdata.getResults().size() > 0){
      SyWeituo weituo =  syWeituoService.findBy("jybh", syFlowdata.getJybh());
      String jl = String.format("本品按 %s 检验上述项目，结果%s符合规定。",
          weituo.getJyyj(),isvalidResult(syFlowdata.getResults()) ? "不": "" );
      syFlowdata.setJyjl(jl);
      saveResults(syFlowdata.getResults(), syFlowdata.getJybh());
    }
    syFlowdata.setModifer(_userid);
    syFlowdata.setModifytime(new Date());
    if (StringUtils.isEmpty(syFlowdata.getId())) {
      syFlowdata.setCreator(_userid);
      syFlowdata.setCreatetime(new Date());
      syFlowdataService.save(syFlowdata);
      message = "新增数据成功。";
    } else {
      syFlowdataService.update(syFlowdata);
      message = "数据更新成功。";
    }
    return ResultGenerator.genSuccessResult(message);
  }

  @PostMapping("/detail")
  public Result detail(@RequestBody SyFlowdata syFlowdata) {
    if (!Strings.isNullOrEmpty(syFlowdata.getId())) {
      syFlowdata = syFlowdataService.findById(syFlowdata.getId());
    } else {
      syFlowdata = syFlowdataService.findBy("jybh", syFlowdata.getJybh());
    }
    return ResultGenerator.genSuccessResult(syFlowdata);
  }

  @PostMapping("/list")
  public Result list(@RequestBody() @Valid PageInfo<SyFlowdata> pageInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
    Condition condition = new Condition(SyFlowdata.class);
    Example.Criteria criteria = condition.createCriteria();
    // TODO 根据页面的查询条件编辑查询条件

    List<SyFlowdata> list = syFlowdataService.findByCondition(condition);
    pageInfo.setTotal(page.getTotal());
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  /**
   * 样品管理
   *
   * @param request
   * @return
   */
  @PostMapping("/samplemanage")
  public Result samplemanage(HttpServletRequest request) {
    String bhs = request.getParameter("jpbhlist");
    String optype = request.getParameter("optype");

    String message = "数据保存成功。";
    super.getUserAccount();
    Condition condition = new Condition(SyFlowdata.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andIn("jybh", Arrays.asList(bhs.split(",")));
    SyFlowdata syflowdata = new SyFlowdata();
    switch (optype) {
      case "1":
        syflowdata.setSyqr(DateUtils.getDate());
        syflowdata.setSjr(_userid);
        break;
      case "2":
        syflowdata.setJyqr(DateUtils.getDate());
        syflowdata.setJysqrr(_userid);
        break;
      case "3":
        syflowdata.setHsqr(DateUtils.getDate());
        syflowdata.setHsqrr(_userid);
        break;
    }
    syFlowdataService.updateByCondition(syflowdata, condition);

    return ResultGenerator.genSuccessResult(message);
  }

  /**
   * 盲样管理
   *
   * @param request
   * @return
   */
  @PostMapping("/blindmanage")
  public Result blindmanage(HttpServletRequest request) {
    String bhs = request.getParameter("jpbhlist");
    String optype = request.getParameter("optype");

    String message = "数据保存成功。";
    super.getUserAccount();
    Condition condition = new Condition(SyFlowdata.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andIn("jybh", Arrays.asList(bhs.split(",")));
    SyFlowdata syflowdata = new SyFlowdata();
    syflowdata.setSfmy("1".equals(optype) ? 1 : 0);
    syFlowdataService.updateByCondition(syflowdata, condition);

    return ResultGenerator.genSuccessResult(message);
  }

  /**
   * 统计对象管理
   *
   * @param request
   * @return
   */
  @PostMapping("/tjdxmanage")
  public Result tjdxmanage(HttpServletRequest request) {
    String bhs = request.getParameter("jpbhlist");
    String optype = request.getParameter("optype");

    String message = "数据保存成功。";
    super.getUserAccount();
    Condition condition = new Condition(SyFlowdata.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andIn("jybh", Arrays.asList(bhs.split(",")));
    SyFlowdata syflowdata = new SyFlowdata();
    syflowdata.setTjdx("1".equals(optype) ? 1 : 0);
    syFlowdataService.updateByCondition(syflowdata, condition);

    return ResultGenerator.genSuccessResult(message);
  }

  /**
   * 判断检验结论是否合格
   * @param results
   * @return  true:不合格, false : 合格
   */
  private boolean isvalidResult(List<SyResult> results){
    for(SyResult r : results){
      if (r.getXmjl().length() > 4) {
        return true;
      }
    }
    return false;
  }

  /**
   * 保存检验结果
   * @param results 检验结果
   * @param jybh  检验编号
   */
  private void saveResults(List<SyResult> results, String jybh){
    // 读取已有数据
    Condition condition = new Condition(SyResult.class);
    Example.Criteria criteria =  condition.createCriteria();
    criteria.andEqualTo("jybh", jybh);
    List<SyResult> exists_datas = syResultService.findByCondition(condition);
    // 已有数据修改（逻辑删除、更新）
    List<SyResult> newdatas = results;
    exists_datas.forEach(e -> {
      SyResult syResult = getEntityById(newdatas, e.getId());
      if (syResult == null) {
        e.setModifytime(new Date());
        e.setModifer(_userid);
        e.setInuse(Integer.valueOf(ProjectConstant.USE_STATUS.NOTUSE.getIndex()));
        syResultService.update(e);
      } else {
        e.setJyzx(syResult.getJyzx());
        e.setJyxx(syResult.getJyxx());
        e.setBzgd(syResult.getBzgd());
        e.setJybh(syResult.getJybh());
        e.setJyjg1(syResult.getJyjg1());
        e.setJyjg2(syResult.getJyjg2());
        e.setJyjg3(syResult.getJyjg3());
        e.setXmjl(syResult.getXmjl());
        e.setInuse(Integer.valueOf(ProjectConstant.USE_STATUS.INUSE.getIndex()));
        e.setModifer(_userid);
        e.setModifytime(new Date());
        syResultService.update(e);
        newdatas.removeIf(n -> e.getId().equals(n.getId()));
      }
    });
    // 新数据追加
    newdatas.forEach(syResult -> {
      if(Strings.isNullOrEmpty(syResult.getId())){
        syResult.setCreator(_userid);
        syResult.setCreatetime(new Date());
        syResult.setInuse(Integer.valueOf(ProjectConstant.USE_STATUS.INUSE.getIndex()));
        syResult.setId(null);
        syResultService.save(syResult);
      } else {
        SyResult e = syResultService.findById(syResult.getId());
        e.setJyzx(syResult.getJyzx());
        e.setJyxx(syResult.getJyxx());
        e.setBzgd(syResult.getBzgd());
        e.setJybh(syResult.getJybh());
        e.setJyjg1(syResult.getJyjg1());
        e.setJyjg2(syResult.getJyjg2());
        e.setJyjg3(syResult.getJyjg3());
        e.setXmjl(syResult.getXmjl());
        e.setInuse(Integer.valueOf(ProjectConstant.USE_STATUS.INUSE.getIndex()));
        e.setModifer(_userid);
        e.setModifytime(new Date());
        syResultService.update(e);
      }
    });
  }

  /**
   * 查询
   * @param datas
   * @param id
   * @return
   */
  private SyResult getEntityById(List<SyResult> datas,String id ){
    for ( SyResult e: datas) {
      if (id.equals(e.getId())) {
        return e;
      }
    }
    return null;
  }

}
