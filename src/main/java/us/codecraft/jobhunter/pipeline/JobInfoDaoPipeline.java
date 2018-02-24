package us.codecraft.jobhunter.pipeline;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.jobhunter.mapper.JobinfoMapper;
import us.codecraft.jobhunter.model.BaseJobInfo;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


@Component("JobInfoDaoPipeline")
public class JobInfoDaoPipeline implements Pipeline {

    @Autowired
    private JobinfoMapper mapper;

    private BaseJobInfo baseJobInfo = new BaseJobInfo();

    public void process(ResultItems resultItems, Task task) {
        baseJobInfo.setCity(resultItems.get("city").toString());
        baseJobInfo.setCompany(resultItems.get("company").toString());
        baseJobInfo.setCompanySize(resultItems.get("companySize").toString());
        baseJobInfo.setDescription(resultItems.get("description").toString());
        baseJobInfo.setEducationRequire(resultItems.get("educationRequire").toString());
        baseJobInfo.setExperience(resultItems.get("experience").toString());
        baseJobInfo.setSalary(resultItems.get("salary").toString());
        baseJobInfo.setSource("http://jobs.zhaopin.com");
        baseJobInfo.setUrl(resultItems.get("url").toString());
        baseJobInfo.setUrlmd5(DigestUtils.md5Hex(resultItems.get("url").toString()));
        baseJobInfo.setTitle(resultItems.get("title").toString());
        mapper.add(baseJobInfo);
    }
}
