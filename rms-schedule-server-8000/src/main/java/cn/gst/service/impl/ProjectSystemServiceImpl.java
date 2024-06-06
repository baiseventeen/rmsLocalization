package cn.gst.service.impl;

import cn.gst.domain.Project;
import cn.gst.domain.ProjectSystem;
import cn.gst.mapper.ProjectSystemMapper;
import cn.gst.query.ProjectSystemHistroyQuery;
import cn.gst.query.ProjectSystemQuery;
import cn.gst.service.ProjectService;
import cn.gst.service.ProjectSystemHistoryService;
import cn.gst.service.ProjectSystemService;
import cn.gst.util.RandomIdUtil;
import cn.gst.vo.PageBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-04-12 16:40
 */
@Service
public class ProjectSystemServiceImpl implements ProjectSystemService {

    @Autowired
    ProjectSystemMapper projectSystemMapper;
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectSystemHistoryService projectSystemHistoryService;

    public ProjectSystem findBySystemId(String systemId) {

        return projectSystemMapper.findBySystemId(systemId);

    }

    public PageBean<ProjectSystem> findBySearch(ProjectSystemQuery query) {

        List<ProjectSystem> projectSystemList = projectSystemMapper.findBySearch(query);

        PageHelper.startPage(query.getPageNow(), query.getPageSize());
        PageInfo<ProjectSystem> pageInfo = new PageInfo<>(projectSystemList);

        PageBean<ProjectSystem> pageBean = new PageBean<>();
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setTotalRows(pageInfo.getTotal());
        pageBean.setRowData(pageInfo.getList());

        return pageBean;

    }

    /**
     * 判断项目下 使用某个技术的系统是否存在，
     * 存在返回true 不存在返回false
     * @param projectId
     * @param developmentTech
     * @return
     */
    public boolean developmentTechIsExist(String projectId, String developmentTech) {
        ProjectSystem projectSystem = projectSystemMapper.findByProjectIdAndDevelopmentTech(projectId, developmentTech);
        if(projectSystem == null){
            return false;
        }
        return true;
    }

    /**
     * 新增system,不记录项目历史，直接更改需要修改的项目信息即可
     * 1. 生成主键
     * 2. 生成systemId
     * 3. 版本号与所属系统一致
     * 4. 添加到 两张数据库表 中
     * 5. 更新项目信息
     * @param projectSystem
     */
    public void add(ProjectSystem projectSystem){
        // 生成主键id RandomIdUtil.createSystemId()
        projectSystem.setId(RandomIdUtil.createId());
        // 生成systemId
        projectSystem.setSystemId(RandomIdUtil.createSystemId());
        projectSystem.setState(1);
        // 如果没有版本号，则查询所属项目，将项目版本赋值给系统
        if(projectSystem.getVersion() == null){
            Project project = projectService.findById(projectSystem.getProjectId());
            projectSystem.setVersion(project.getVersion());
        }
        projectSystem.setTotalWorkload(projectSystem.getDesignWorkload()+
                projectSystem.getDemandWorkload()+
                projectSystem.getDevelopWorkload()+
                projectSystem.getTestWorkload()+
                projectSystem.getOnlineWorkload());
        // 插入
        projectSystemMapper.add(projectSystem);
        projectSystemMapper.addSystem(projectSystem);

        //-----------------------更改项目信息

    }

    /**
     * 修改system
     * 需要记录项目、系统的历史
     * 1. 查出项目，记录并更新
     * 2. 查出系统，记录并更新
     *
     * 如果system_id不存在则代表只记录并更新系统，不记录更新项目
     * @param projectSystem
     */
    public void recordAndUpdate(ProjectSystem projectSystem){
        String projectId = projectSystem.getProjectId();
        if(projectSystem.getSystemId() != null){
            // 1.查出项目，记录并更新
            Project project = projectService.findById(projectId);
            //-----------------------更改项目信息

            projectService.update(project);
        }
        String newVersion = projectService.findById(projectId).getVersion();
        // 2.查出系统，记录并更新
        List<ProjectSystem> projectSystemList = projectSystemMapper.findByProjectId(projectId);
        for (ProjectSystem system : projectSystemList) {
            // 新增历史记录
            projectSystemHistoryService.add(system);
            // 如果是要修改的那一项，则不适用查到的数据，而使用传入的参数数据
            if (projectSystem.getSystemId() != null && system.getSystemId().equals(projectSystem.getSystemId())) {
                projectSystem.setVersion(newVersion);
                update(projectSystem);
                continue;
            }
            // 更新版本号并更新数据
            system.setVersion(newVersion);
            update(system);
        }

    }

    /**
     * 记录并删除当前系统，并恢复历史版本系统，
     */
    public void recordAndUpdateByProjectRestore(String projectId, String version){
        deleteByProjectId(projectId);
        // 查找到历史版本
        ProjectSystemHistroyQuery query = new ProjectSystemHistroyQuery();
        query.setProjectId(projectId);
        query.setVersion(version);
        List<ProjectSystem> list = projectSystemHistoryService.findListBySearch(query);
        // 恢复并赋予新版本号
        String newVersion = projectService.findById(projectId).getVersion();
        for(ProjectSystem system: list){
            system.setVersion(newVersion);
            add(system);
        }
    }

    /**
     * 将新数据覆盖到表中
     * @param newProjectSystem
     */
    public void update(ProjectSystem newProjectSystem){
        projectSystemMapper.update(newProjectSystem);
        projectSystemMapper.updateSystem(newProjectSystem);
    }

    /**
     * 通过system_id删除系统,系统和项目会更新
     * 1. 记录并更新
     * 2. 删除指定系统
     * @param systemId
     */
    public void deleteById(String systemId){
        // 1.记录并更新系统和项目
        ProjectSystem projectSystem = projectSystemMapper.findBySystemId(systemId);
        recordAndUpdate(projectSystem);
        // 2.删除指定系统
        projectSystemMapper.delete(projectSystem.getSystemId());
        projectSystemMapper.deleteSystem(projectSystem.getSystemId());
    }

    /**
     * 通过project_id记录和删除系统,项目不更新
     * 1. 记录
     * 2. 删除指定系统
     * @param projectId
     */
    public void deleteByProjectId(String projectId){
        // 1.记录
        List<ProjectSystem> projectSystemList = projectSystemMapper.findByProjectId(projectId);
        for(ProjectSystem system: projectSystemList){
            projectSystemHistoryService.add(system);
            // 2.删除
            projectSystemMapper.delete(system.getSystemId());
            projectSystemMapper.deleteSystem(system.getSystemId());
        }

    }

}
