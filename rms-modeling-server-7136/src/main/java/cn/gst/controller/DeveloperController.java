package cn.gst.controller;

import cn.gst.domain.Developer;
import cn.gst.exception.AppException;
import cn.gst.exception.AppExceptionCodeMsg;
import cn.gst.query.DeveloperQuery;
import cn.gst.service.DeveloperService;
import cn.gst.vo.PageBean;
import cn.gst.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/developerManage",produces = "application/json;charset=utf-8")
public class DeveloperController {

    @Autowired
    DeveloperService developerService;

    //TODO:岗位设置
    @PostMapping("/add")
    public ResultBean add(@RequestBody Developer developer){
        if(developer==null){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        if(StringUtils.isEmpty(developer.getName())
            ||StringUtils.isEmpty(developer.getBirthday())
            ||StringUtils.isEmpty(developer.getPhone())
            ||StringUtils.isEmpty(developer.getOnboardingTime())
            ||StringUtils.isEmpty(developer.getCollege())
            ||StringUtils.isEmpty(developer.getEducationBackground())
            ||StringUtils.isEmpty(developer.getSalary())
            ){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        developerService.add(developer);
        return ResultBean.success();
    }

    @DeleteMapping("/{id}")
    public ResultBean del(@PathVariable("id") String id){
        if(StringUtils.isEmpty(id)){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        developerService.delete(id);
        return ResultBean.success();
    }

    @PutMapping("/update")
    public ResultBean update(@RequestBody Developer developer){
        if(developer==null){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        if(StringUtils.isEmpty(developer.getId())
                ||StringUtils.isEmpty(developer.getName())
                ||StringUtils.isEmpty(developer.getBirthday())
                ||StringUtils.isEmpty(developer.getPhone())
                ||StringUtils.isEmpty(developer.getOnboardingTime())
                ||StringUtils.isEmpty(developer.getCollege())
                ||StringUtils.isEmpty(developer.getEducationBackground())
                ||StringUtils.isEmpty(developer.getSalary())
        ){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        developerService.update(developer);
        return ResultBean.success();
    }

    @PostMapping("/page")
    public ResultBean<PageBean<Developer>> page(@RequestBody DeveloperQuery developerQuery){
        if(developerQuery==null){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        return ResultBean.success(developerService.page(developerQuery));
    }

    @GetMapping("/findAll")
    public ResultBean findAll(){
        return ResultBean.success(developerService.findAll());
    }
}
