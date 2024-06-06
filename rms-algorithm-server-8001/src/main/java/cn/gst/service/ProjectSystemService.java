package cn.gst.service;

import cn.gst.domain.Project;
import cn.gst.domain.ProjectSystem;
import cn.gst.mapper.AlgorithmProjectSystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 柏琪
 * @date 2024-04-12 16:40
 */
@Service
public class ProjectSystemService{

    @Autowired
    AlgorithmProjectSystemMapper algorithmProjectSystemMapper;

    public ProjectSystem findBySystemId(String systemId) {

        return algorithmProjectSystemMapper.findBySystemId(systemId);

    }

    public Date getLastEndTimeBySystemId(String systemId){
        return algorithmProjectSystemMapper.getLastEndTimeBySystemId(systemId);
    }

    public Map<String, String> findProjectBySystemId(String systemId){
        Map<String, String> result = new HashMap<>();
        Map<String, Project> map = algorithmProjectSystemMapper.findProjectBySystemId(systemId);
        map.forEach((key, project)->{
            result.put("projectId", project.getId());
            result.put("projectName", project.getName());
        });
        return result;
    }
    
    public void evaluate(List<String> systemIdList){
        for(int i = 0; i < systemIdList.size(); i ++){
            ProjectSystem projectSystem = algorithmProjectSystemMapper.findBySystemId(systemIdList.get(i));
            projectSystem.setPriorityScore(90.0);
            algorithmProjectSystemMapper.updateSystem(projectSystem);
        }
    }

}
