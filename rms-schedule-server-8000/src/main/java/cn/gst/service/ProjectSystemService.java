package cn.gst.service;

import cn.gst.domain.ProjectSystem;
import cn.gst.query.ProjectSystemQuery;
import cn.gst.vo.PageBean;

/**
 * @author 柏琪
 * @date 2024-04-12 16:40
 */
public interface ProjectSystemService {

    public ProjectSystem findBySystemId(String systemId);

    public PageBean<ProjectSystem> findBySearch(ProjectSystemQuery query);
    /**
     * 判断项目下 使用某个技术的系统是否存在，
     * 存在返回true 不存在返回false
     * @param projectId
     * @param developmentTech
     * @return
     */
    public boolean developmentTechIsExist(String projectId, String developmentTech);
    /**
     * 新增system
     * 不要记录项目历史，直接更改需要修改的项目信息即可
     * 1. 生成主键
     * 2. 生成systemId
     * 3. 版本号与所属系统一致
     * 4. 添加到 两张数据库表 中
     * 5. 更新项目信息
     * @param projectSystem
     */
    public void add(ProjectSystem projectSystem);
    /**
     * 修改system
     * 需要记录项目、系统的历史
     * 1. 查出项目，记录并更新
     * 2. 查出系统，记录并更新
     * @param projectSystem
     */
    public void recordAndUpdate(ProjectSystem projectSystem);
    /**
     * 仅仅将新数据覆盖到表中
     * @param newProjectSystem
     */
    public void update(ProjectSystem newProjectSystem);
    /**
     * 通过system_id删除系统,系统和项目会更新
     * 1. 记录并更新
     * 2. 删除指定系统
     * @param systemId
     */
    public void deleteById(String systemId);
    /**
     * 通过project_id记录和删除系统,项目不更新
     * 1. 记录
     * 2. 删除指定系统
     * @param projectId
     */
    public void deleteByProjectId(String projectId);
    /**
     * 记录并删除当前系统，并恢复历史版本系统，
     */
    public void recordAndUpdateByProjectRestore(String projectId, String version);
}
