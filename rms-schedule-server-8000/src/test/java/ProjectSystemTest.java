import cn.gst.ScheduleServerApplication;
import cn.gst.domain.ProjectSystem;
import cn.gst.mapper.ProjectSystemMapper;
import cn.gst.query.ProjectSystemHistroyQuery;
import cn.gst.service.ProjectSystemHistoryService;
import cn.gst.service.ProjectSystemService;
import cn.gst.vo.PageBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 柏琪
 * @date 2024-04-12 15:46
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScheduleServerApplication.class)
public class ProjectSystemTest {

    @Autowired
    ProjectSystemMapper projectSystemMapper;
    @Autowired
    ProjectSystemService projectSystemService;
    @Autowired
    ProjectSystemHistoryService projectSystemHistoryService;

    @Test
    public void TestFindAll(){
        System.out.println("-----------findAll-----------");
        List<ProjectSystem> systemList = projectSystemMapper.findAll();
        System.out.println("-----------findAll-----------");
        systemList.forEach(System.out::println);

        systemList.clear();
        System.out.println("-----------findByProjectId-----------");
        systemList = projectSystemMapper.findByProjectId("20240412-1712905532653");
        System.out.println("-----------findByProjectId-----------");
        systemList.forEach(System.out::println);

        System.out.println("-----------findBySystemId-----------");
        ProjectSystem system = projectSystemMapper.findBySystemId("123-123");
        System.out.println("-----------findBySystemId-----------");
        System.out.println(system);

    }

    @Test
    public void testFindall(){
        List<ProjectSystem> list = projectSystemMapper.findByProjectId("20240412-1712905532653");
        list.forEach(System.out::println);
    }

    @Test
    public void testAdd(){
        // ok
        ProjectSystem projectSystem = new ProjectSystem();
        projectSystem.setName("h5开发");
        projectSystem.setDevelopmentTech("h5-id-1231");
        projectSystem.setProjectId("20240412-1712905532653");
        projectSystemService.add(projectSystem);
    }

    @Test
    public void delete(){
        ProjectSystem projectSystem = projectSystemMapper.findBySystemId("20240415-1713166380879");
        projectSystem.setName("E路自驾java开发");
        projectSystemMapper.update(projectSystem);
        projectSystemMapper.updateSystem(projectSystem);
    }

    @Test
    public void find(){
        ProjectSystemHistroyQuery query = new ProjectSystemHistroyQuery();
        query.setProjectId("20240412-1712905532653");
        PageBean<ProjectSystem> pageBean = projectSystemHistoryService.findBySearch(query);
        pageBean.getRowData().forEach(System.out::println);
    }

    @Test
    public void deleteSer() throws InterruptedException {
//        ProjectSystemQuery projectSystemQuery = new ProjectSystemQuery();
//        projectSystemQuery.setProjectId("20240412-1712905532653");
////        PageBean<ProjectSystem> list = projectSystemService.findByProjectId(projectSystemQuery);
////        List<ProjectSystem> lists = list.getRowData();
//        lists.forEach(System.out::println);
//        projectSystemService.deleteByProjectId("20240412-1712905532653");
//        for(int i = 0; i < 10; i++){
//            Thread.sleep(1000);
//            System.out.println(i);
//        }
//        for(ProjectSystem system: lists){
//            projectSystemService.add(system);
//        }

    }

    @Test
    public void update() {
        ProjectSystem projectSystem = projectSystemService.findBySystemId("20240416-1713235272470");
        projectSystem.setName("java开发");
        projectSystemService.recordAndUpdate(projectSystem);
    }


}
