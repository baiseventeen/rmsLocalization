package cn.gst.service.impl;

import cn.gst.domain.DevelopmentTech;
import cn.gst.mapper.DevelopmentTechMapper;
import cn.gst.service.DevelopmentTechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DevelopmentTechServiceImp implements DevelopmentTechService {

    @Autowired
    DevelopmentTechMapper developmentTechMapper;

    @Override
    public void add(DevelopmentTech developmentTech) {
        developmentTech.setId(UUID.randomUUID().toString().replace("-", ""));
        developmentTechMapper.add(developmentTech);
    }

    @Override
    public Map<String, Object> findAll() {
        return developmentTechMapper.findAll();
    }
}
