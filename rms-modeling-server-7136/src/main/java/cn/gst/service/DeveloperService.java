package cn.gst.service;

import cn.gst.domain.Developer;
import cn.gst.query.DeveloperQuery;
import cn.gst.vo.PageBean;

import java.util.List;

public interface DeveloperService {

    void add(Developer developer);

    void delete(String id);

    void update(Developer developer);

    PageBean<Developer> page(DeveloperQuery developerQuery);

    List<Developer> findAll();
}
