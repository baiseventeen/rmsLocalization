package cn.gst.mapper;

import cn.gst.entity.Scheduling;
import cn.gst.entity.SchedulingPeriod;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-13 12:39
 */
@Repository
public interface AlgorithmSchedulingMapper {

    void add(@Param("scheduling") Scheduling scheduling);
    List<Scheduling> findAll();
    List<String> findDeveloperNamesBySystemId(@Param("systemId")String systemId);
}
