package us.codecraft.jobhunter.model;


import lombok.Data;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://jobs.51job.com/*")
@HelpUrl("http://search.51job.com/list/%s,000000,0000,00,9,99,%s,2,%d.html?&industrytype=01")
@Data
@ToString
public class Job51Info extends BaseJobInfo implements AfterExtractor {
    @ExtractBy("//h1/text()")
    private String title="";
    @ExtractBy("//div[@class='cn']/strong/text()")
    private String salary="";
    @ExtractBy("//p[@class='cname']/a/text()")
    private String company="";
    @ExtractBy("//div[@class='bmsg job_msg inbox']/allText()")
    private String description="";
    private String source="http://jobs.51job.com";
    @ExtractBy("//span[@class='lname']/text()")
    private String city;
    @ExtractBy("//div[@class='t1']/span[2]/text()")
    private String educationRequire;
    @ExtractBy("//div[@class='t1']/span[1]/text()")
    private String experience;
    @ExtractByUrl
    private String url="";
    private String urlmd5;


    public void setDescription(String description) {
        if (description!=null){
            this.description = description;
        }
    }

    public void setUrl(String url) {
        this.url = url;
        this.urlmd5 = DigestUtils.md5Hex(url);
    }

    public void afterProcess(Page page) {

    }
}
