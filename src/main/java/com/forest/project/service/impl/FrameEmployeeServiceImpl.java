package com.forest.project.service.impl;

import com.forest.project.dao.FrameEmployeeMapper;
import com.forest.project.model.FrameEmployee;
import com.forest.project.service.FrameEmployeeService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/04/11.
 */
@Service
@Transactional
public class FrameEmployeeServiceImpl extends AbstractService<FrameEmployee> implements FrameEmployeeService {
    // 需要手工追加查询逻辑才添加
//    @Resource
//    private FrameEmployeeMapper frameEmployeeMapper;
    @Override
    public FrameEmployee getByUserId(String userid) {
        return super.findBy("userid", userid);
    }

}
