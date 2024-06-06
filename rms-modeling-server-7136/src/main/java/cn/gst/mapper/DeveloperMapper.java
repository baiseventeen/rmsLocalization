package cn.gst.mapper;

import cn.gst.domain.Developer;
import cn.gst.query.DeveloperQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperMapper{

    void add(Developer developer);

    void delete(String id);

    void update(Developer developer);

    List<Developer> page(@Param("queryInfo") DeveloperQuery query);

    List<Developer> findAll();
}
