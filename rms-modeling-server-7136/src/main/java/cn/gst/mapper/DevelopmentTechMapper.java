package cn.gst.mapper;

import cn.gst.domain.DevelopmentTech;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface DevelopmentTechMapper {

    void add(DevelopmentTech developmentTech);

    @MapKey("development_tech_id")
    Map<String,Object> findAll();

}
