package com.forest.core;

import com.forest.project.model.FrameEmployee;
import com.forest.project.service.FrameEmployeeService;
import com.forest.project.service.FrameSettingService;
import com.forest.utils.*;
import com.github.pagehelper.PageInfo;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController<T> {
    protected String _userid;
    protected FrameEmployee _user;
    protected String _currentYear;
    @Resource
    protected FrameSettingService frameSettingService;

    @Resource
    protected FrameEmployeeService frameEmployeeService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        //true:允许输入空值，false:不能为空值
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 获取当前登录用户账号
     * @return
     */
    protected String getUserAccount(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        _userid = JWTAuthenticationUtil.getUserId(req);
        _user = frameEmployeeService.findBy("userid", _userid);
        return JWTAuthenticationUtil.getUserId(req);
//        _userid = "hellen";
//        return _userid;
    }

    protected Object getItem(PageInfo<T> pageInfo, String itemName){
        if(pageInfo.getList() == null || pageInfo.getList().size() <= 0){
            return null;
        } else {
            try {
                Class<?> clz = pageInfo.getList().get(0).getClass();
                Field field = clz.getDeclaredField(itemName);//通过反射，通过键获取类的所有的名称和数据类型
                String fieldType = field.getGenericType().toString();//通过反射获取键的数据类型
                String methodName = "get";
                if (("class java.lang.Boolean").equals(fieldType)) {//布尔类型的是特殊的
                    methodName = "is";
                }
                //通过反射，得到对象的get方法和数据格式
                Method m = (Method) clz.getMethod(
                        methodName.concat(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, itemName)));
                Object value = m.invoke(pageInfo.getList().get(0));//反射调用对象里面的方法
                return value;
            } catch (Exception e){
                return null;
            }
        }
    }

    /**
     * 格式化字符串,有效字符串trim()结果
     * @param obj
     * @return
     */
    protected String formatString(Object obj){
        return obj == null ? null : String.valueOf(obj).trim();
    }

    /**
     * 获取兽药检验年度
     * @return
     */
    protected  String getSyJynd(){
//        _currentYear = frameSettingService.findBy("code","ywnd").getContent().trim();
//        return _currentYear;
        return ScsyResourceUtil.getCurrentYear();
    }

    /**
     * 根据残留检验项目自动生成新的检验编号
     * @param typeid	检验项目
     * @return 检验编号
     */
    public String getNewNumberofRemain(String typeid){
       return ScsyResourceUtil.getNewNumberofRemain(typeid);
    }

    /**
     * 根据兽药检验目的自动生成新的内部检验编号
     * @param typeid	检验目的
     * @return 检验编号
     */
    public String getNewNumberofDrugs(String typeid){
        return ScsyResourceUtil.getNewNumberofDrugs(typeid);
    }

    /**
     * 读取兽药检验报告归档文件
     * @param jybh
     * @return
     */
    public String getGuidangFileofDrugs(String jybh) {
        String filepath = ScsyReportUtil.getRealArchiveReportPath().concat(ScsyReportUtil.SY_SUBDICTIONARY)
            .concat(jybh).concat(ScsyReportUtil.PDF_SUFFIX);
        if (FileUtils.exists(filepath)) {
            return "/".concat(filepath.replace(ScsyReportUtil.getSystemRootPath(), ""));
        } else {
            return null;
        }
    }

    /**
     * 读取残留检验报告归档文件
     * @param jybh
     * @return
     */
    public String getGuidangFileofRemain(String jybh) {
        String filepath = ScsyReportUtil.getRealArchiveReportPath().concat(ScsyReportUtil.CL_SUBDICTIONARY)
            .concat(jybh).concat(ScsyReportUtil.PDF_SUFFIX);
        if (FileUtils.exists(filepath)) {
            return filepath.replace(ScsyReportUtil.getSystemRootPath(), "");
        } else {
            return null;
        }
    }
}
