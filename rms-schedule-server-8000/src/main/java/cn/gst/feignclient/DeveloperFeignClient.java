package cn.gst.feignclient;

import cn.gst.domain.Developer;
import cn.gst.query.DeveloperQuery;
import cn.gst.vo.PageBean;
import cn.gst.vo.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 柏琪
 * @date 2024-04-22 11:02
 */
@FeignClient("modeling-server")
public interface DeveloperFeignClient {
    @PostMapping("/developerManage/add")
    public ResultBean add(@RequestBody Developer developer);
    @DeleteMapping("/developerManage/{id}")
    public ResultBean del(@PathVariable("id") String id);
    @PutMapping("/developerManage/update")
    public ResultBean update(@RequestBody Developer developer);
    @PostMapping("/developerManage/page")
    public ResultBean<PageBean<Developer>> page(@RequestBody DeveloperQuery developerQuery);
}
