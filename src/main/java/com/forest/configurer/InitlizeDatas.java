package com.forest.configurer;

import com.forest.cl.model.ClWeituo;
import com.forest.cl.service.ClDetectitemService;
import com.forest.cl.service.ClWeituoService;
import com.forest.project.model.FrameDictionary;
import com.forest.project.service.FrameDictionaryService;
import com.forest.project.service.FrameOrganizationService;
import com.forest.project.service.FrameSettingService;
import com.forest.sy.model.SyJymd;
import com.forest.sy.model.SyWeituo;
import com.forest.sy.service.SyJymdService;
import com.forest.sy.service.SyWeituoService;
import com.forest.utils.ScsyResourceUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitlizeDatas implements ApplicationRunner {
    @Resource
    FrameOrganizationService frameOrganizationService;
    @Resource
    FrameDictionaryService frameDictionaryService;
    @Resource
    SyJymdService syJymdService;
    @Resource
    protected FrameSettingService frameSettingService;
    @Resource
    private SyWeituoService syWeituoService;
    @Resource
    private ClDetectitemService clDetectitemService;
    @Resource
    private ClWeituoService clWeituoService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 缓存字典数据
        List<FrameDictionary> list = frameDictionaryService.findAll();
        list.forEach(e->{
            ScsyResourceUtil.pushDicitionary(e.getId(), e.getName());
        });

        // 缓存单位数据
        Map<String, String> units = new HashMap<String, String>();
        frameOrganizationService.findAll().forEach(e->{
            units.put(e.getId(), e.getName());
        });
        ScsyResourceUtil.setOrganization(units);

        // 缓存检验目的数据
        Map<String, String> jymds = new HashMap<String, String>();
        List<SyJymd> jymdlst = syJymdService.findAll();
        jymdlst.forEach(e->{
            jymds.put(e.getId(), e.getJymdName());
            ScsyResourceUtil.pushForamts(e.getId(), e.getJymdBmgz());
        });
        ScsyResourceUtil.setCheckTargets(jymds);

        String year = frameSettingService.findBy("code",ScsyResourceUtil.KEY_OF_CURRENTYEAR).getContent().trim();
        ScsyResourceUtil.setCurrentYear(year);
        // 缓存各类数据最大编号(兽药)
        jymdlst.forEach(e->{
            Page page = PageHelper.startPage(1, 5,true);
            Condition condition = new Condition(SyWeituo.class);
            Example.Criteria criteria =  condition.createCriteria();
            criteria.andEqualTo("jymd", e.getId()).andEqualTo("jynd", year);
            condition.orderBy("jybh").desc();

            List<SyWeituo>  ls = syWeituoService.findByCondition(condition);
            if(ls.size() > 0) {
                ScsyResourceUtil.pushDrugsNo(e.getId(), ls.get(0).getJybh());
            }
        });
        // 缓存各类数据最大编号(残留)
        Page page = PageHelper.startPage(1, 5,true);
        Condition condition2 = new Condition(ClWeituo.class);
        Example.Criteria criteria2 =  condition2.createCriteria();
        criteria2.andEqualTo("inuse", 1).andEqualTo("cljynd", year);
        condition2.orderBy("jybh").desc();

        List<ClWeituo>  ls2 = clWeituoService.findByCondition(condition2);
        if(ls2.size() > 0) {
            ScsyResourceUtil.pushReaminNo(ls2.get(0).getJybh());
        }
/*        clDetectitemService.findAll().forEach(e->{
            ScsyResourceUtil.pushForamts(e.getId(), e.getItemrule());
            Page page = PageHelper.startPage(1, 5,true);
            Condition condition2 = new Condition(ClWeituo.class);
            Example.Criteria criteria2 =  condition2.createCriteria();
            criteria2.andEqualTo("jyxm", e.getId()).andEqualTo("cljynd", year);
            condition2.orderBy("jybh").desc();

            List<ClWeituo>  ls2 = clWeituoService.findByCondition(condition2);
            if(ls2.size() > 0) {
                ScsyResourceUtil.pushReaminNo(e.getId(), ls2.get(0).getJybh());
            }
        });*/
    }
}
