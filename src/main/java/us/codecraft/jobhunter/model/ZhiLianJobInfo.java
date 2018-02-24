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

@TargetUrl("http://jobs.zhaopin.com/*")
@HelpUrl("http://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000")
@Data
@ToString
public class ZhiLianJobInfo extends BaseJobInfo implements AfterExtractor {
    @ExtractBy("//div[@class='fl']/h1/text()")
    private String title="";
    @ExtractBy("//ul[@class='terminal-ul']/li/strong/text()")
    private String salary="";
    @ExtractBy("//div[@class='fl']/h2/a/text()")
    private String company="";
    @ExtractBy("//div[@class='tab-inner-cont']/allText()")
    private String description="";
    private String source="http://jobs.zhaopin.com/";
    @ExtractBy("//ul[@class='terminal-ul']/li[2]/strong/text()")
    private String city;
    @ExtractBy("//ul[@class='terminal-ul']/li[6]/strong/text()")
    private String educationRequire;
    @ExtractBy("//ul[@class='terminal-ul']/li[5]/strong/text()")
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
