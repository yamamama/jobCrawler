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

@TargetUrl("https://www.zhipin.com/job_detail/*")
@HelpUrl("https://www.zhipin.com/job_detail/*")
@Data
@ToString
public class BossJobInfo extends BaseJobInfo implements AfterExtractor {
    @ExtractBy("//div[@class='job-primary']/div[2]/div[2]/h1/text()")
    private String title="";
    @ExtractBy("//span[@class='badge']/text()")
    private String salary="";
    @ExtractBy("//div[@class='info-company']/h3/a/text()")
    private String company="";
    @ExtractBy("//div[@class='detail-content']/div/div/allText()")
    private String description="";
    private String source="http://www.zhipin.com";

    private String city;
    private String educationRequire;
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
        String txt = page.getHtml().$("div.info-primary p", "text").get();
        String[] split = txt.split("：");
        this.city=split[1].split("经验")[0];
        this.experience=split[2].split("学历")[0];
        this.educationRequire=split[3];
    }
}
