package cn.gst.service.impl;

import cn.gst.domain.Developer;
import cn.gst.mapper.DeveloperMapper;
import cn.gst.query.DeveloperQuery;
import cn.gst.service.DeveloperService;
import cn.gst.vo.PageBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class DeveloperServiceImp implements DeveloperService {

    @Autowired
    DeveloperMapper developerMapper;

    @Override
    public void add(Developer developer) {
        developer.setId(UUID.randomUUID().toString().replace("-", ""));
        developerMapper.add(developer);
    }

    @Override
    public void delete(String id) {
        developerMapper.delete(id);
    }

    @Override
    public void update(Developer developer) {
        developerMapper.update(developer);
    }

    @Override
    public PageBean<Developer> page(DeveloperQuery developerQuery) {
        PageBean<Developer> pageBean = new PageBean<>();

        PageHelper.startPage(developerQuery.getPageNow(), developerQuery.getPageSize());
        PageInfo<Developer> pageInfo = new PageInfo<>(developerMapper.page(developerQuery));

        pageBean.setTotalRows(pageInfo.getTotal());
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setRowData(pageInfo.getList());

        return pageBean;
    }

    @Override
    public List<Developer> findAll() {
        return developerMapper.findAll();
    }
}
