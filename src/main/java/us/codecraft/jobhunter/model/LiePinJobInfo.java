package us.codecraft.jobhunter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;


@TargetUrl("https://www.liepin.com/job/*")
@HelpUrl("https://www.liepin.com/sojob/?dqs=020&curPage=\\d+")
@Setter
@Getter
@ToString
public class LiePinJobInfo extends BaseJobInfo implements AfterExtractor {
    @ExtractBy("//h1/text()")
    private String title="";
    @ExtractBy("//p[@class='job-item-title']/text()")
    private String salary="";
    @ExtractBy("//div[@class='title-info']/h3/a/text()")
    private String company="";
    @ExtractBy("//div[@class='content content-word']/allText()")
    private String description="";
    private String source="liepin.com";
    @ExtractBy("//p[@class='basic-infor']/span/a/text()")
    private String city;
    @ExtractBy("//div[@class='job-qualifications']/span[1]/text()")
    private String educationRequire;
    @ExtractBy("//div[@class='job-qualifications']/span[2]/text()")
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
