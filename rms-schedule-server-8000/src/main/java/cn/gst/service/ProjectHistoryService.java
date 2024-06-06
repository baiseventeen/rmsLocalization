package cn.gst.service;

import cn.gst.domain.Project;
import cn.gst.mapper.ProjectHistoryMapper;
import cn.gst.query.ProjectHistroyQuery;
import cn.gst.vo.PageBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjectHistoryService {

    public PageBean<Project> findByProjectId(ProjectHistroyQuery projectHistroyQuery);

    public Project findByProjectIdAndVersion(ProjectHistroyQuery projectHistroyQuery);

    public void add(Project project);
    /**
     * 恢复历史版本
     * @param projectId 项目id
     * @param version   版本号
     */
    public void restore(String projectId, String version);

}
