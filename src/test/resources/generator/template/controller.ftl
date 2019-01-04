package ${basePackage}.${subPackage}web;
import ${basePackage}.core.Result;
import ${basePackage}.core.ResultGenerator;
import ${basePackage}.core.BaseController;
import ${basePackage}.${subPackage}model.${modelNameUpperCamel};
import ${basePackage}.${subPackage}service.${modelNameUpperCamel}Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Date;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller extends BaseController {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.deleteById(${modelNameLowerCamel}.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() ${modelNameUpperCamel} ${modelNameLowerCamel},BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        ${modelNameLowerCamel}.setModifer(_userid);
        ${modelNameLowerCamel}.setModifytime(new Date());
        if(StringUtils.isEmpty(${modelNameLowerCamel}.getId())){
            ${modelNameLowerCamel}.setCreator(_userid);
            ${modelNameLowerCamel}.setCreatetime(new Date());
            ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
            message ="新增数据成功。";
        } else {
            ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(${modelNameLowerCamel}.getId());
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<${modelNameUpperCamel}> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(${modelNameUpperCamel}.class);
        Example.Criteria criteria =  condition.createCriteria();
        // TODO 根据页面的查询条件编辑查询条件

        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
