package cn.gst.mapper;

import cn.gst.domain.Position;
import cn.gst.query.PositionQuery;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface PositionMapper {

    void add(Position p);

    void delete(String id);

    void update(Position p);

    List<Position> page(@Param("queryInfo") PositionQuery positionQuery);

    @MapKey("position_level_id")
    Map<Integer,String> findLevel();
}
