package com.forest.project.dao;

import com.forest.core.Mapper;
import com.forest.project.model.FrameResource;

import java.util.List;

public interface FrameResourceMapper extends Mapper<FrameResource> {
    List<FrameResource> getPermessions(String userid);
}