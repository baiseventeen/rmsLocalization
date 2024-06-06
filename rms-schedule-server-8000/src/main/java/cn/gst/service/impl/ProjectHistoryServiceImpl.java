package cn.gst.service.impl;

import cn.gst.domain.Project;
import cn.gst.mapper.ProjectHistoryMapper;
import cn.gst.query.ProjectHistroyQuery;
import cn.gst.service.ProjectHistoryService;
import cn.gst.service.ProjectService;
import cn.gst.vo.PageBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectHistoryServiceImpl implements ProjectHistoryService {

    @Autowired
    ProjectHistoryMapper projectHistoryMapper;
    @Autowired
    ProjectService projectService;

    public PageBean<Project> findByProjectId(ProjectHistroyQuery projectHistroyQuery){

        // 查询得到的结果集
        List<Project> projectList = projectHistoryMapper.findByProjectId(projectHistroyQuery.getProjectId());

        // 开启分页
        PageHelper.startPage(projectHistroyQuery.getPageNow(), projectHistroyQuery.getPageSize());
        // 创建分页对象
        PageInfo<Project> pageInfo = new PageInfo<>(projectList);

        // 构建结果对象
        PageBean<Project> pageBean = new PageBean<>();
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setTotalRows(pageInfo.getTotal());
        pageBean.setRowData(pageInfo.getList());

        return pageBean;

    }

    public Project findByProjectIdAndVersion(ProjectHistroyQuery projectHistroyQuery){

        return projectHistoryMapper.findByProjectIdAndVersion(projectHistroyQuery.getProjectId(), projectHistroyQuery.getVersion());

    }

    public void add(Project project){
        projectHistoryMapper.add(project);
    }

    /**
     * 恢复项目及下属系统历史版本
     * @param projectId 项目id
     * @param version   版本号
     */
    public void restore(String projectId, String version) {
        // 1.查找要恢复的项目信息
        ProjectHistroyQuery query = new ProjectHistroyQuery();
        query.setProjectId(projectId);
        query.setVersion(version);
        Project newProject = findByProjectIdAndVersion(query);
        // 2.更新项目信息，包括历史记录等
        newProject.setVersion(version);
        projectService.recordAndUpdate(newProject);
    }

}
