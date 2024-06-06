package cn.gst.mapper;

import cn.gst.entity.Investment;
import cn.gst.gantt.ProjectGanttTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-13 12:39
 */
@Repository
public interface InvestmentMapper {

    void add(@Param("investment")Investment investment);

    List<Investment> findBySearch(@Param("developerId") String developerId);
}
