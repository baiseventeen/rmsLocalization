package cn.gst.mapper;

import cn.gst.domain.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public interface ProjectHistoryMapper {

    List<Project> findAll();
    List<Project> findByProjectId(String projectId);
    Project findByProjectIdAndVersion(@Param("projectId") String projectId,@Param("version") String version);

    void add(@Param("project") Project project);

}
