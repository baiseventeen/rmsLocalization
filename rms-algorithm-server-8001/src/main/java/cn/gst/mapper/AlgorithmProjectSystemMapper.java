package cn.gst.mapper;

import cn.gst.domain.Project;
import cn.gst.domain.ProjectSystem;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 柏琪
 * @date 2024-04-12 15:34
 */
@Repository
public interface AlgorithmProjectSystemMapper {

    List<ProjectSystem> findAll();
    List<ProjectSystem> findByProjectId(@Param("projectId") String projectId);
    ProjectSystem findBySystemId(@Param("systemId") String systemId);
    ProjectSystem findByProjectIdAndDevelopmentTech(@Param("projectId") String projectId, @Param("developmentTech") String developmentTech);

    void add(@Param("projectSystem")ProjectSystem projectSystem);
    void addSystem(@Param("projectSystem")ProjectSystem projectSystem);

    void update(@Param("projectSystem")ProjectSystem projectSystem);
    void updateSystem(@Param("projectSystem")ProjectSystem projectSystem);

    void delete(@Param("systemId") String systemId);
    void deleteSystem(@Param("systemId") String systemId);

    Date getLastEndTimeBySystemId(@Param("systemId") String systemId);

    @MapKey("id")
    Map<String, Project> findProjectBySystemId(@Param("systemId") String systemId);
}
