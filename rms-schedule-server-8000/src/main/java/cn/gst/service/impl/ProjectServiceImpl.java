package cn.gst.service.impl;

import cn.gst.domain.Project;
import cn.gst.mapper.ProjectMapper;
import cn.gst.query.ProjectQuery;
import cn.gst.service.ProjectHistoryService;
import cn.gst.service.ProjectService;
import cn.gst.service.ProjectSystemService;
import cn.gst.util.VersionUtil;
import cn.gst.vo.PageBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectHistoryService projectHistoryService;
    @Autowired
    ProjectSystemService projectSystemService;

    public Project findById(String id){
        return projectMapper.findById(id);
    }

    public PageBean<Project> findBySeacrh(ProjectQuery projectQuery) {

        // 查询得到的结果集
        List<Project> projectList = projectMapper.findBySearch(projectQuery);

        // 开启分页
        PageHelper.startPage(projectQuery.getPageNow(), projectQuery.getPageSize());
        // 创建分页对象
        PageInfo<Project> pageInfo = new PageInfo<>(projectList);

        // 构建结果对象
        PageBean<Project> pageBean = new PageBean<>();
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setTotalRows(pageInfo.getTotal());
        pageBean.setRowData(pageInfo.getList());
        return pageBean;

    }

    public void add(Project project) {
        //生成project_id,版本，创建时间，状态
        String version = VersionUtil.create();
        project.setId(version);
        project.setVersion(version);
        project.setCreateTime(new Date());
        project.setState(1);
        projectMapper.add(project);
    }

    /**
     * 完成系统及所属项目一起更新
     * 1. 首先将项目表中的旧项目信息保存到历史表
     * 2. 然后将新项目信息加入项目表
     * 3. 系统更新记录
     */
    public void recordAndUpdate(Project newProject) {
        String version = VersionUtil.create();
        newProject.setVersion(version);
        //1.在project_history表中新增一项
        Project oldProject = findById(newProject.getId());
        projectHistoryService.add(oldProject);
        //2.更新project表信息
        update(newProject);
        //3.更新下属系统
        projectSystemService.recordAndUpdateByProjectRestore(newProject.getId(), version);
    }

    /**
     * 更新项目信息
     * @param newProject
     * @return String 返回项目新建版本号
     */
    public void update(Project newProject){
        // 更新版本号及创建日期
        String version = VersionUtil.create();
        newProject.setVersion(version);
        newProject.setCreateTime(new Date());
        projectMapper.update(newProject);
    }

    /**
     * 仅记录并删除项目
     * @param id
     */
    public void deleteById(String id) {
        // 记录
        Project project = findById(id);
        projectHistoryService.add(project);
        // 删除
        projectMapper.deleteById(id);
    }

    /**
     * 记录并删除项目及系统
     * @param id
     */
    public void deleteProject(String id){
        //删除并记录项目
        deleteById(id);
        //删除并记录系统
        projectSystemService.deleteByProjectId(id);
    }
}
