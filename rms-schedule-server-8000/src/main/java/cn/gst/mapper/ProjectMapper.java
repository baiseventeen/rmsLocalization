package cn.gst.mapper;

import cn.gst.domain.Project;
import cn.gst.query.ProjectQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {

    List<Project> findAll();
    Project findById(@Param("id") String id);
    List<Project> findBySearch(@Param("query") ProjectQuery query);

    void add(@Param("project") Project project);

    void update(@Param("project") Project project);

    void deleteById(@Param("id") String id);
}
