package cn.gst.service.impl;

import cn.gst.domain.Position;
import cn.gst.mapper.PositionMapper;
import cn.gst.query.PositionQuery;
import cn.gst.service.PositionService;
import cn.gst.vo.PageBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PositionServiceImp implements PositionService {

    @Autowired
    PositionMapper positionMapper;

    @Override
    public void add(Position position) {
        position.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        positionMapper.add(position);
    }

    @Override
    public void delete(String id) {
        positionMapper.delete(id);
    }

    @Override
    public void update(Position position) {
        positionMapper.update(position);
    }

    @Override
    public PageBean<Position> page(PositionQuery positionQuery) {
        PageBean<Position> pageBean = new PageBean<>();

        PageHelper.startPage(positionQuery.getPageNow(),positionQuery.getPageSize());
        PageInfo<Position> pageInfo = new PageInfo<>(positionMapper.page(positionQuery));
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setTotalRows(pageInfo.getTotal());
        pageBean.setRowData(pageInfo.getList());

        return pageBean;
    }

    @Override
    public Map<Integer, String> findLevel() {
        return positionMapper.findLevel();
    }
}
