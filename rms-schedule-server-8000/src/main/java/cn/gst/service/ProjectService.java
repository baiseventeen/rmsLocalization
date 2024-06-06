package cn.gst.service;

import cn.gst.domain.Project;
import cn.gst.query.ProjectQuery;
import cn.gst.vo.PageBean;

public interface ProjectService {

    public Project findById(String id);

    public PageBean<Project> findBySeacrh(ProjectQuery projectQuery);

    public void add(Project project);
    /**
     * 完成系统及所属项目一起更新
     * 1. 首先将项目表中的旧项目信息保存到历史表
     * 2. 然后将新项目信息加入项目表
     * 3. 系统更新记录
     */
    public void recordAndUpdate(Project newProject);
    /**
     * 仅更新项目信息
     * @param newProject
     * @return String 返回项目新建版本号
     */
    public void update(Project newProject);
    /**
     * 仅记录并删除项目
     * @param id
     */
    public void deleteById(String id);
    /**
     * 记录并删除项目及系统
     * @param id
     */
    public void deleteProject(String id);

}
