package com.forest.project.web;

import com.forest.core.BaseModel;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.dao.FrameRoleMapper;
import com.forest.project.model.FrameEmployee;
import com.forest.project.model.FrameRolemember;
import com.forest.project.service.FrameEmployeeService;
import com.forest.project.service.FrameRolememberService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Date;

/**
 * Created by CodeGenerator on 2018/04/11.
 */
@RestController
@RequestMapping("/frame/employee")
public class FrameEmployeeController extends BaseController {
  @Resource
  private FrameEmployeeService frameEmployeeService;
  //    private String _userid = "admin";
  @Resource
  private FrameRolememberService frameRolememberService;

  @PostMapping("/delete")
  public Result delete(@RequestBody() FrameEmployee frameEmployee) {
    frameEmployeeService.deleteById(frameEmployee.getId());
    return ResultGenerator.genSuccessResult();
  }

  @PostMapping("/deletemore")
  public Result deletemore(@RequestBody() String ids) {
    frameEmployeeService.deleteByIds(ids);
    return ResultGenerator.genSuccessResult();
  }

  @PostMapping("/save")
  public Result save(@RequestBody() FrameEmployee frameEmployee, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    super.getUserAccount();
    String message = "数据保存成功。";
    frameEmployee.setModifer(_userid);
    frameEmployee.setModifytime(new Date());
    if (StringUtils.isEmpty(frameEmployee.getId())) {
      frameEmployee.setCreator(_userid);
      frameEmployee.setCreatetime(new Date());
      frameEmployeeService.save(frameEmployee);
      message = "新增数据成功。";
    } else {
      frameEmployeeService.update(frameEmployee);
      message = "数据更新成功。";
    }
    Condition condition = new Condition(FrameRolemember.class);
    condition.createCriteria().andEqualTo("userId", frameEmployee.getId());
    if(null != frameEmployee.getRoles()) {
      frameRolememberService.deleteByCondition(condition);
//    rms.forEach(rm ->{
//      if (frameEmployee.getRoles().contains(rm.getRoleId())) {
//        rm.setInuse("1");
//      } else {
//        rm.setInuse("9");
//      }
//      frameRolememberService.save(rm);
//    });
      frameEmployee.getRoles().forEach(r -> {
        FrameRolemember rm = new FrameRolemember();
        rm.setInuse("1");
        rm.setUserId(frameEmployee.getId());
        rm.setRoleId(r);
        rm.setCreator(_userid);
        rm.setCreatetime(new Date());
        rm.setModifer(_userid);
        rm.setModifytime(new Date());
        frameRolememberService.save(rm);
      });
    }
    return ResultGenerator.genSuccessResult(message);
  }

  @PostMapping("/setpass")
  public Result setpass(HttpServletRequest req,@RequestParam(value = "p1") String p1,@RequestParam(value = "p2") String p2) {
    super.getUserAccount();
//    String p1 = req.getParameter("oldp");
////    String p2 = req.getParameter("newp");
    String message = "密码修改成功。";
    if(Strings.isNullOrEmpty(p1)){
      message = "原始密码未设置。";
      return ResultGenerator.genFailResult(message);
    } else if (Strings.isNullOrEmpty(p2)) {
      message = "新密码未设置。";
      return ResultGenerator.genFailResult(message);
    } else if (!_user.getPassword().equals(p1)) {
      message = "原始密码错误。";
      return ResultGenerator.genFailResult(message);
    } else {
      FrameEmployee frameEmployee = _user;
      frameEmployee.setPassword(p2);
      frameEmployee.setModifer(_userid);
      frameEmployee.setModifytime(new Date());
      frameEmployeeService.update(frameEmployee);
      return ResultGenerator.genSuccessResult(message);
    }
  }

  @PostMapping("/detail")
  public Result detail(@RequestBody FrameEmployee frameEmployee) {
    if (!Strings.isNullOrEmpty(frameEmployee.getId())) {
      frameEmployee = frameEmployeeService.findById(frameEmployee.getId());
    } else {
      frameEmployee = frameEmployeeService.findBy("userid", frameEmployee.getUserid());
    }
    return ResultGenerator.genSuccessResult(frameEmployee);
  }

  @PostMapping("/list")
  public Result list(@RequestBody() @Valid PageInfo<FrameEmployee> pageInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
    }
    Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
    Condition condition = new Condition(FrameEmployee.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andNotEqualTo("orgId", BaseModel.ENTERPRISE_UNIT_ID);
    // 根据页面的查询条件编辑查询条件
    Optional<FrameEmployee> employeeOptional = Optional.fromNullable(pageInfo.getList().get(0));
    if (employeeOptional.isPresent()) {
      Optional<String> keyword = Optional.fromNullable(employeeOptional.get().getName());
      String key = keyword.or("");
      if (!Strings.isNullOrEmpty(key)) {
        Example.Criteria criterialike = condition.createCriteria();
        String likekey = Joiner.on("").join("", "%", key, "%", "");
        String sql = "( name like ".concat(likekey).concat(" or code like ").concat(likekey).concat(") ");
        criterialike.orLike("name", likekey).orLike("code", likekey);
        condition.and(criterialike);
      }
      String orgId = Optional.fromNullable(employeeOptional.get().getOrgId()).or("");
      if (!Strings.isNullOrEmpty(orgId)) {
        criteria.andEqualTo("orgId", orgId);
      }
    }

    List<FrameEmployee> list = frameEmployeeService.findByCondition(condition);
    pageInfo.setTotal(page.getTotal());
    pageInfo.setList(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

}
