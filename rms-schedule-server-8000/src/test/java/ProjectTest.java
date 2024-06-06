import cn.gst.ScheduleServerApplication;
import cn.gst.domain.Project;
import cn.gst.mapper.ProjectMapper;
import cn.gst.query.ProjectQuery;
import cn.gst.service.ProjectHistoryService;
import cn.gst.service.ProjectService;
import cn.gst.vo.PageBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScheduleServerApplication.class)
public class ProjectTest {

    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectHistoryService projectHistoryService;

    @Test
    public void Test01(){
        System.out.println(projectMapper.findById("1"));
    }

    @Test
    public void TestFindBySearch(){
        ProjectQuery projectQuery = new ProjectQuery();
//        projectQuery.setResponsiblePerson("就");
        projectQuery.setStartLastEndTime(new Date());
        projectQuery.setEndLastEndTime(new Date());
        List<Project> project = projectMapper.findBySearch(projectQuery);
        System.out.println(project);
    }

    @Test
    public void TestAdd(){
        Project project = new Project();
        project.setName("测试数据库连接");
        project.setCreateTime(new Date());
        projectService.add(project);
    }

    @Test
    public void TestUpdate(){
        Project project = projectService.findById("20240412-1712905532653");
        project.setName(project.getName()+"第二版");
        projectService.recordAndUpdate(project);
    }

    @Test
    public void TestList(){
        ProjectQuery query = new ProjectQuery();
        PageBean<Project> pageBean = projectService.findBySeacrh(query);
        System.out.println(pageBean.getTotalPages());
        System.out.println(pageBean.getTotalRows());
        pageBean.getRowData().forEach(System.out::println);
    }

    @Test
    public void TestRestore(){
        projectHistoryService.restore("20240412-1712891032810", "20240412-1712898663632");
    }

    @Test
    public void restore(){
        projectHistoryService.restore("20240412-1712905532653", "20240416-1713235272406");
    }
}
