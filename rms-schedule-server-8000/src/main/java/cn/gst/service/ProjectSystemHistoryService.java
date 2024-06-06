package cn.gst.service;

import cn.gst.domain.ProjectSystem;
import cn.gst.query.ProjectSystemHistroyQuery;
import cn.gst.vo.PageBean;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjectSystemHistoryService{

    public PageBean<ProjectSystem> findBySearch(ProjectSystemHistroyQuery projectSystemHistroyQuery);
    public List<ProjectSystem> findListBySearch(ProjectSystemHistroyQuery projectSystemHistroyQuery);
    /**
     * 通过project_system_id主键查询历史版本系统
     * @param id 即project_system_id
     * @return
     */
    public ProjectSystem findById(String id);

    public void add(ProjectSystem projectSystem);
    /**
     * 恢复历史版本
     * @param projectId 项目id
     * @param version   版本号
     */
//    public void restore(String projectId, String version);

}