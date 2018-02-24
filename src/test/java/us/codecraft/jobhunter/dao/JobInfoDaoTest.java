package us.codecraft.jobhunter.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import us.codecraft.jobhunter.mapper.JobinfoMapper;
import us.codecraft.jobhunter.model.LiePinJobInfo;

import javax.annotation.Resource;


@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext-*.xml"})
public class JobInfoDaoTest {
    @Resource
    private JobinfoMapper jobInfoDAO;

    @Test
    public void test() {
        LiePinJobInfo jobInfo = new LiePinJobInfo();
        jobInfo.setDescription("哈哈");

        try {
            final int add = jobInfoDAO.add(jobInfo);
            System.out.println(add);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
