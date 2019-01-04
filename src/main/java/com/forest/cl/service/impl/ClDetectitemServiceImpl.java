package com.forest.cl.service.impl;

import com.forest.cl.dao.ClDetectitemMapper;
import com.forest.cl.model.ClDetectitem;
import com.forest.cl.service.ClDetectitemService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/08/22.
 */
@Service
@Transactional
public class ClDetectitemServiceImpl extends AbstractService<ClDetectitem> implements ClDetectitemService {

//     需要手工追加查询逻辑才添加
/*
    @Resource
    private ClDetectitemMapper clDetectitemMapper;
*/

    @Override
    public Map<String, String> getMapdata() {
        List<ClDetectitem> xms =  super.findAll();
        Map<String, String> map = new HashMap<String, String>();
        xms.forEach(e->{map.put(e.getId(), e.getItemname());});
        return map;
    }
}
