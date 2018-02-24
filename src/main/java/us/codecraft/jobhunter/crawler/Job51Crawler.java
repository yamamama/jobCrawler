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
public class Job51Crawler implements PageProcessor {

    @Autowired
    private Pipeline jobInfoDaoPipeline;

    private static final String prefix = "http://search.51job.com/jobsearch/search_result.php?funtype=0100&industrytype=01&curr_page=";

    private Site site = Site.me().setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Encoding","gzip, deflate")
            .addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
            .addHeader("Connection","keep-alive")
            .addHeader("Cache-Control","no-cache")
            .addHeader("Cookie","guid=15192887767483480024; search=jobarea%7E%60000000%7C%21ord_field%7E%600%7C%21recentSearch0%7E%601%A1%FB%A1%FA000000%2C00%A1%FB%A1%FA000000%A1%FB%A1%FA0000%A1%FB%A1%FA01%A1%FB%A1%FA9%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA%A1%FB%A1%FA2%A1%FB%A1%FA%A1%FB%A1%FA-1%A1%FB%A1%FA1519288899%A1%FB%A1%FA0%A1%FB%A1%FA%A1%FB%A1%FA%7C%21recentSearch1%7E%601%A1%FB%A1%FA000000%2C00%A1%FB%A1%FA000000%A1%FB%A1%FA0000%A1%FB%A1%FA32%A1%FB%A1%FA9%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA%A1%FB%A1%FA2%A1%FB%A1%FA%A1%FB%A1%FA-1%A1%FB%A1%FA1519289438%A1%FB%A1%FA0%A1%FB%A1%FA%A1%FB%A1%FA%7C%21recentSearch2%7E%601%A1%FB%A1%FA000000%2C00%A1%FB%A1%FA000000%A1%FB%A1%FA0000%A1%FB%A1%FA00%A1%FB%A1%FA9%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA%A1%FB%A1%FA2%A1%FB%A1%FA%A1%FB%A1%FA-1%A1%FB%A1%FA1519288800%A1%FB%A1%FA0%A1%FB%A1%FA%A1%FB%A1%FA%7C%21recentSearch3%7E%601%A1%FB%A1%FA030200%2C00%A1%FB%A1%FA000000%A1%FB%A1%FA0000%A1%FB%A1%FA00%A1%FB%A1%FA9%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA99%A1%FB%A1%FA%A1%FB%A1%FA2%A1%FB%A1%FA%A1%FB%A1%FA-1%A1%FB%A1%FA1519288776%A1%FB%A1%FA0%A1%FB%A1%FA%A1%FB%A1%FA%7C%21collapse_expansion%7E%601%7C%21; nsearch=jobarea%3D%26%7C%26ord_field%3D%26%7C%26recentSearch0%3D%26%7C%26recentSearch1%3D%26%7C%26recentSearch2%3D%26%7C%26recentSearch3%3D%26%7C%26recentSearch4%3D%26%7C%26collapse_expansion%3D; 51job=cuid%3D124282347%26%7C%26cusername%3Dphone_13247318065%26%7C%26cpassword%3D%26%7C%26cname%3D%25D4%25F8%25BD%25A3%25B7%25E6%26%7C%26cemail%3Dzxcang%2540outlook.com%26%7C%26cemailstatus%3D3%26%7C%26cnickname%3D%26%7C%26ccry%3D.0l3mf.y%252Ft66.%26%7C%26cconfirmkey%3Dzxk2xoFj9M8%252FM%26%7C%26cresumeids%3D.0wik0vrB697Y%257C%26%7C%26cautologin%3D1%26%7C%26cenglish%3D0%26%7C%26sex%3D0%26%7C%26cnamekey%3DzxTTuSLM6JhHY%26%7C%26to%3DWmNQOQRiV2NcNQ5gBWNVYgE1A35aLAY7UjtcYF8wBVoLMgBoDm4DMVUzWDcCZgY9U2BWYVRnASgANwViDjEPP1pqUDkEbldoXDoOYA%253D%253D%26%7C%26; slife=lowbrowser%3Dnot%26%7C%26lastlogindate%3D20180223%26%7C%26; ps=us%3DWWdWPA9zAzRUMQtnB3xWZAcyAzIBKVY2ATZWeFtgU2ZaYwdlBGQBPgFiXTgAYlBhU2ECNVdjVH1QZQAnXWNSB1kW%26%7C%26needv%3D0; _ujz=MTI0MjgyMzQ3MA%3D%3D; partner=www_baidu_com")
            .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0")
            .addHeader("Upgrade-Insecure-Requests","1")
            .setCharset("gb2312");

    public void crawl() {
        //提前把展示列表的url统一放入 可到2000页
        List<String> urls = new ArrayList<String>();
        for (int i = 1; i <= 1; i++) {
            String url = prefix+i;
            urls.add(url);
        }

        OOSpider.create(new Job51Crawler())
                .startUrls(urls)
                .addPipeline(jobInfoDaoPipeline)
                .thread(5)
                .run();
    }

    public void process(Page page) {
        if (page.getUrl().toString().matches("http://search.51job.com/jobsearch/([\\s\\S]*?)")){
            //从页面发现后续的url地址来抓取;
            List<String> hrefs = page.getHtml().$("div.el p.t1 span a", "href")
                    .regex("http://jobs.51job.com/\\w+.*html").all();
            page.addTargetRequests(hrefs);
        }else {
            page.putField("title", page.getHtml().xpath("//h1/text()").toString());
            page.putField("salary", page.getHtml().xpath("//div[@class='cn']/strong/text()").toString());
            page.putField("company", page.getHtml().xpath("//p[@class='cname']/a/text()").toString());
            page.putField("description", page.getHtml().xpath("//div[@class='bmsg job_msg inbox']/allText()").toString());
            page.putField("source", "http://jobs.51job.com");
            page.putField("city", page.getHtml().xpath("//span[@class='lname']/text()").toString());
            page.putField("educationRequire", page.getHtml().xpath("//div[@class='t1']/span[2]/text()").toString());
            page.putField("experience", page.getHtml().xpath("//div[@class='t1']/span[1]/text()").toString());
            page.putField("url",page.getUrl().toString() );
            page.putField("urlmd5", DigestUtils.md5Hex(page.getUrl().toString()) );
            String[] split = page.getHtml().xpath("//p[@class='msg']/text()").toString().split("\\|");
            page.putField("companySize",split.length<=2?"-1":split[1].toString());
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final Job51Crawler jobCrawler = applicationContext.getBean(Job51Crawler.class);
        jobCrawler.crawl();
    }
}
