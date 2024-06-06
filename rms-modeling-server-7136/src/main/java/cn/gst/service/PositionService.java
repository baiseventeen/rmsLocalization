package cn.gst.service;

import cn.gst.domain.Position;
import cn.gst.query.PositionQuery;
import cn.gst.vo.PageBean;

import java.util.Map;

public interface PositionService {

    void add(Position position);

    void delete(String id);

    void update(Position position);

    PageBean<Position> page(PositionQuery positionQuery);

    Map<Integer, String> findLevel();

}
