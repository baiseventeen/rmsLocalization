package cn.gst.mapper;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface LoginMapper {


    Map findById(String id);

}
