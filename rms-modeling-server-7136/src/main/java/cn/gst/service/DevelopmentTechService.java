package cn.gst.service;

import cn.gst.domain.DevelopmentTech;

import java.util.Map;

public interface DevelopmentTechService {

    void add(DevelopmentTech developmentTech);

    Map<String, Object> findAll();
}
