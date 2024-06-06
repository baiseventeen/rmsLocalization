package cn.gst.service.impl;

import cn.gst.mapper.LoginMapper;
import cn.gst.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Transactional(readOnly = true)
    @Override
    public Map findById(String id) {
        return loginMapper.findById(id);
    }
}
