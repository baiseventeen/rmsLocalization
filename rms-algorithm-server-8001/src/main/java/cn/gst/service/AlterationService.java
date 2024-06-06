package cn.gst.service;

import cn.gst.domain.Project;
import cn.gst.entity.Alteration;
import cn.gst.mapper.AlterationMapper;
import cn.gst.query.AlterationQuery;
import cn.gst.query.BaseQuery;
import cn.gst.util.RandomIdUtil;
import cn.gst.vo.PageBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 柏琪
 * @date 2024-05-20 16:37
 */
@Service
public class AlterationService {
    @Autowired
    AlterationMapper alterationMapper;
    @Autowired
    AlgorithmDeveloperService algorithmDeveloperService;

    public void add(Alteration alteration){
        Date date = new Date();
        if(alteration.getId() == null){
            alteration.setId(RandomIdUtil.getUUID());
        }
        alteration.setRequestDate(date);
        alteration.setState(0);
        // 说明是项目经理提出的请求
        if(alteration.getLevel() == 2){
            alteration.setProcessDeveloperId(alteration.getRequestDeveloperId());
            alteration.setProcessDate(date);
        }
        alterationMapper.add(alteration);
    }

    public void update(Alteration alteration){
        alterationMapper.update(alteration);
    }

    public PageBean<Alteration> findWithManager(AlterationQuery alterationQuery){
        // 查询得到的结果集
        List<Alteration> alterationList = alterationMapper.findWithManager();
        for(Alteration alteration: alterationList){
            alteration.setRequestDeveloperName(algorithmDeveloperService.findById(alteration.getRequestDeveloperId()).getName());
        }

        // 开启分页
        PageHelper.startPage(alterationQuery.getPageNow(), alterationQuery.getPageSize());
        // 创建分页对象
        PageInfo<Alteration> pageInfo = new PageInfo<>(alterationList);

        // 构建结果对象
        PageBean<Alteration> pageBean = new PageBean<>();
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setTotalRows(pageInfo.getTotal());
        pageBean.setRowData(pageInfo.getList());

        return pageBean;
    }

    public PageBean<Alteration> findWithAdmin(AlterationQuery alterationQuery){
        // 查询得到的结果集
        List<Alteration> alterationList = alterationMapper.findWithAdmin();
        for(Alteration alteration: alterationList){
            alteration.setRequestDeveloperName(algorithmDeveloperService.findById(alteration.getRequestDeveloperId()).getName());
            alteration.setProcessDeveloperName(algorithmDeveloperService.findById(alteration.getProcessDeveloperId()).getName());
        }
        // 开启分页
        PageHelper.startPage(alterationQuery.getPageNow(), alterationQuery.getPageSize());
        // 创建分页对象
        PageInfo<Alteration> pageInfo = new PageInfo<>(alterationList);

        // 构建结果对象
        PageBean<Alteration> pageBean = new PageBean<>();
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setTotalRows(pageInfo.getTotal());
        pageBean.setRowData(pageInfo.getList());

        return pageBean;
    }

    public PageBean<Alteration> findByRequestDeveloperId(AlterationQuery alterationQuery){
        // 查询得到的结果集
        List<Alteration> alterationList = alterationMapper.findByRequestDeveloperId(alterationQuery.getDeveloperId());

        // 开启分页
        PageHelper.startPage(alterationQuery.getPageNow(), alterationQuery.getPageSize());
        // 创建分页对象
        PageInfo<Alteration> pageInfo = new PageInfo<>(alterationList);

        // 构建结果对象
        PageBean<Alteration> pageBean = new PageBean<>();
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setTotalRows(pageInfo.getTotal());
        pageBean.setRowData(pageInfo.getList());

        return pageBean;
    }
}
