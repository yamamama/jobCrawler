package us.codecraft.jobhunter.crawler;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;


@Component
public class ZhongHuaJobCrawler implements PageProcessor{

    @Autowired
    private Pipeline jobInfoDaoPipeline;

    private static final String prefix = "http://www.chinahr.com/jobs/57468/";

    private Site site = Site.me().setRetryTimes(3)
           .setSleepTime(1000)
           .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
           .addHeader("Accept-Encoding","gzip, deflate")
           .addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
           .addHeader("Connection","keep-alive")
           .addHeader("Cache-Control","no-cache")
           .addHeader("Cookie","RecentVisitCity=291_guangzhou; RecentVisitCityFullpathPc=\"25,291\"; chrId=7c8608c3084c443a90f2b2a8908bb754; gr_user_id=8c8b22e8-7efa-4870-9586-d8b937a95af2; 58tj_uuid=070d6e00-61a8-4ba8-90d6-94bc355413dc; new_uv=3; wmda_uuid=348c850005728370939f051ebf4f6e4f; wmda_new_uuid=1; wmda_visited_projects=%3B1732047435009; gtid=c93f2b5a0e5c46f28455e9db70c3ae0e; wmda_session_id_1732047435009=1519397840701-3d2a672b-bd83-7dc0; gr_session_id_be17cdb1115be298=1d8dfa89-a69e-4358-844f-251e6894d3ba; channel=social; new_session=0; init_refer=http%253A%252F%252Fwww.chinahr.com%252Fjob%252F5932690949867777.html; utm_source=; spm=; __utma=162484963.8659565.1519398159.1519398159.1519398159.1; __utmb=162484963.1.10.1519398159; __utmc=162484963; __utmz=162484963.1519398159.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmt=1; uniq=ecee4c12b0e5445092bea6d7542e897c; PPS=\"lt=1521990291276&st=1519830291276&lts=84deb7d4dd9c&sts=04a11e58112f&uid=5356b0e4932d905a0185f055j&uname=&tid=5356b0e4932d905a0185f055j1519398291276\"; loginTime=1519398291")
           .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0")
           .addHeader("Upgrade-Insecure-Requests","1").setCharset("UTF-8");

    public void crawl() {
        //提前把展示列表的url统一放入
        List<String> urls = new ArrayList<String>();
        for (int i = 1; i <= 1; i++) {
            String url = prefix+i;
            urls.add(url);
        }

        OOSpider.create(new ZhongHuaJobCrawler())
                .startUrls(urls)
                .addPipeline(jobInfoDaoPipeline)
                .thread(5)
                .run();
    }



    public void process(Page page) {
        if (page.getUrl().toString().matches("http://www.chinahr.com/jobs/57468/([\\s\\S]*?)")){
            //从页面发现后续的url地址来抓取;
            List<String> hrefs = page.getHtml().$("li.l1 span.e1 a", "href")
                    .regex("http://www.chinahr.com/job/\\w+.html").all();
            page.addTargetRequests(hrefs);
        }else {
            page.putField("title", page.getHtml().xpath("//span[@class='job_name']/text()").toString());
            page.putField("salary", page.getHtml().xpath("//span[@class='job_price']/text()").toString());
            page.putField("company", page.getHtml().xpath("//div[@class='job-company']/h4/a/text()").toString());
            page.putField("description", page.getHtml().xpath("//div[@class='job_intro_info']/allText()").toString());
            page.putField("source", "http://www.chinahr.com");
            page.putField("city", page.getHtml().xpath("//span[@class='job_loc']/text()").toString());
            page.putField("educationRequire", page.getHtml().xpath("//div[@class='job_require']/span[4]/text()").toString());
            page.putField("experience", page.getHtml().xpath("//span[@class='job_exp']/text()").toString());
            page.putField("url",page.getUrl().toString() );
            page.putField("urlmd5", DigestUtils.md5Hex(page.getUrl().toString()) );
            page.putField("companySize",page.getHtml().xpath("//div[@class='job-company']/table/tbody/tr[3]/td[2]/text()").toString());
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final ZhongHuaJobCrawler jobCrawler = applicationContext.getBean(ZhongHuaJobCrawler.class);
        jobCrawler.crawl();
    }
}
