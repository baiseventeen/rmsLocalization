package cn.gst.service.impl;

import cn.gst.domain.ProjectSystem;
import cn.gst.mapper.ProjectSystemHistoryMapper;
import cn.gst.query.ProjectSystemHistroyQuery;
import cn.gst.service.ProjectSystemHistoryService;
import cn.gst.vo.PageBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectSystemHistoryServiceImpl implements ProjectSystemHistoryService {

    @Autowired
    ProjectSystemHistoryMapper projectSystemHistoryMapper;

    public PageBean<ProjectSystem> findBySearch(ProjectSystemHistroyQuery projectSystemHistroyQuery){

        // 查询得到的结果集
        List<ProjectSystem> projectSystemList = projectSystemHistoryMapper.findBySearch(projectSystemHistroyQuery);

        // 开启分页
        PageHelper.startPage(projectSystemHistroyQuery.getPageNow(), projectSystemHistroyQuery.getPageSize());
        // 创建分页对象
        PageInfo<ProjectSystem> pageInfo = new PageInfo<>(projectSystemList);

        // 构建结果对象
        PageBean<ProjectSystem> pageBean = new PageBean<>();
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setTotalRows(pageInfo.getTotal());
        pageBean.setRowData(pageInfo.getList());

        return pageBean;

    }

    public List<ProjectSystem> findListBySearch(ProjectSystemHistroyQuery projectSystemHistroyQuery){
        return projectSystemHistoryMapper.findBySearch(projectSystemHistroyQuery);
    }

    /**
     * 通过project_system_id主键查询历史版本系统
     * @param id 即project_system_id
     * @return
     */
    public ProjectSystem findById(String id){
        return projectSystemHistoryMapper.findById(id);
    }


    public void add(ProjectSystem projectSystem){
        projectSystemHistoryMapper.add(projectSystem);
    }
//
//    /**
//     * 恢复历史版本
//     * @param projectId 项目id
//     * @param version   版本号
//     */
//    public void restore(String projectId, String version) {
//
//        ProjectHistroyQuery query = new ProjectHistroyQuery();
//        query.setProjectId(projectId);
//        query.setVersion(version);
//        Project newProject = findByProjectIdAndVersion(query);
//        projectService.update(newProject);
//
//    }

}
