package cn.gst.mapper;

import cn.gst.domain.Project;
import cn.gst.domain.ProjectSystem;
import cn.gst.query.ProjectSystemHistroyQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectSystemHistoryMapper {

//    List<ProjectSystem> findByProjectId(String projectId);
//    /**
//     * 查询同项目下 同版本 不同技术 的系统
//     * @param projectId
//     * @param version
//     * @return
//     */
//    List<ProjectSystem> findByProjectIdAndVersion(@Param("projectId") String projectId,@Param("version") String version);
//    /**
//     * 查询同项目下 同技术 不同版本 的系统
//     * @param projectId
//     * @param version
//     * @return
//     */
//    List<ProjectSystem> findByProjectIdAndDevelopmentTech(@Param("projectId") String projectId,@Param("version") String version);
    List<ProjectSystem> findBySearch(@Param("query")ProjectSystemHistroyQuery projectSystemHistroyQuery);
    ProjectSystem findById(@Param("id") String id);

    void add(@Param("projectSystem") ProjectSystem projectSystem);

}
