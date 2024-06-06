package cn.gst.mapper;

import cn.gst.domain.Developer;
import cn.gst.entity.DPP;
import cn.gst.query.DeveloperQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlgorithmDeveloperMapper {

    List<DPP> getDppByDeveloperId(@Param("developerId") String developerId);
    Developer findById(@Param("id") String id);
    List<Developer> findAll();
}
