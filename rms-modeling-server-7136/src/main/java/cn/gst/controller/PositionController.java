package cn.gst.controller;

import cn.gst.domain.Position;
import cn.gst.exception.AppException;
import cn.gst.exception.AppExceptionCodeMsg;
import cn.gst.query.PositionQuery;
import cn.gst.service.PositionService;
import cn.gst.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/positionManage",produces = "application/json;charset=utf-8")
public class PositionController {

    @Autowired
    PositionService positionService;

    @PostMapping("/add")
    public ResultBean add(@RequestBody Position position){
        if(position==null){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        if(StringUtils.isEmpty(position.getPositionName())
            ||position.getPositionLevelId()==null
            ||position.getProductivity()==null){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        positionService.add(position);

        return ResultBean.success();
    }

    @DeleteMapping("/{id}")
    public ResultBean del(@PathVariable("id") String id){
        if(StringUtils.isEmpty(id)){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        positionService.delete(id);
        return ResultBean.success();
    }

    @PutMapping("/update")
    public ResultBean update(@RequestBody Position position){
        if(position==null){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        if(StringUtils.isEmpty(position.getPositionName())
                ||position.getPositionLevelId()==null
                ||position.getProductivity()==null){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        positionService.update(position);

        return ResultBean.success();
    }

    @PostMapping("/page")
    public ResultBean page(@RequestBody PositionQuery positionQuery){
        if(positionQuery==null){
            throw new AppException(AppExceptionCodeMsg.REQUEST_PARAM_EMPTY);
        }
        return ResultBean.success(positionService.page(positionQuery));
    }

    @GetMapping("/findLevel")
    public ResultBean findLevel(){
        return ResultBean.success(positionService.findLevel());
    }
}
