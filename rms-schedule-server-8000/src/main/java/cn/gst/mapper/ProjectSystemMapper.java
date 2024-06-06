package cn.gst.mapper;

import cn.gst.domain.ProjectSystem;
import cn.gst.query.ProjectSystemQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-04-12 15:34
 */
@Repository
public interface ProjectSystemMapper {

    List<ProjectSystem> findAll();
    List<ProjectSystem> findByProjectId(@Param("projectId") String projectId);
    ProjectSystem findBySystemId(@Param("systemId") String systemId);
    ProjectSystem findByProjectIdAndDevelopmentTech(@Param("projectId") String projectId, @Param("developmentTech") String developmentTech);
    List<ProjectSystem> findBySearch(@Param("query") ProjectSystemQuery query);

    void add(@Param("projectSystem")ProjectSystem projectSystem);
    void addSystem(@Param("projectSystem")ProjectSystem projectSystem);

    void update(@Param("projectSystem")ProjectSystem projectSystem);
    void updateSystem(@Param("projectSystem")ProjectSystem projectSystem);

    void delete(@Param("systemId") String systemId);
    void deleteSystem(@Param("systemId") String systemId);
}
