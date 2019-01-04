package com.forest.project.service;
import com.forest.project.model.FrameEmployee;
import com.forest.core.Service;

/**
 * Created by CodeGenerator on 2018/04/11.
 */
public interface FrameEmployeeService extends Service<FrameEmployee> {

    public FrameEmployee getByUserId(String userid);
}
