package cn.gst.service;

import cn.gst.domain.Developer;
import cn.gst.entity.DPP;
import cn.gst.mapper.AlgorithmDeveloperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-08 14:44
 */
@Service
public class AlgorithmDeveloperService {

    @Autowired
    AlgorithmDeveloperMapper algorithmDeveloperMapper;

    public List<DPP> getDppByDeveloperId(String developerId){
        return algorithmDeveloperMapper.getDppByDeveloperId(developerId);
    }

    public List<DPP> getDppByDeveloperIdList(ArrayList<String> developerIdList){
        List<DPP> dppList = new ArrayList<>();
        for(String developerId: developerIdList){
            dppList.addAll(getDppByDeveloperId(developerId));
        }
        return dppList;
    }

    public Developer findById(String id){
        return algorithmDeveloperMapper.findById(id);
    }

    public List<Developer> findAll(){

        return algorithmDeveloperMapper.findAll();
    }
}
