package cn.gst.mapper;

import cn.gst.entity.Alteration;
import cn.gst.entity.Investment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-13 12:39
 */
@Repository
public interface AlterationMapper {

    void add(@Param("alteration")Alteration alteration);
    void update(@Param("alteration")Alteration alteration);
    List<Alteration> findWithManager();
    List<Alteration> findWithAdmin();
    List<Alteration> findByRequestDeveloperId(@Param("developerId")String developerId);
//    List<Alteration> findBySearch();
}
