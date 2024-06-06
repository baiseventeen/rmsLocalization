package cn.gst.service;

import cn.gst.entity.Investment;
import cn.gst.mapper.InvestmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-18 23:36
 */
@Service
public class InvestmentService {
    @Autowired
    InvestmentMapper investmentMapper;

    public void add(Investment investment){
        investmentMapper.add(investment);
    }

    public List<Investment> fingBySearch(String developerId){
        return investmentMapper.findBySearch(developerId);
    }
}
