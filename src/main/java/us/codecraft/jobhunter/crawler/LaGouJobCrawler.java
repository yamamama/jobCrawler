package us.codecraft.jobhunter.crawler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Component
public class LaGouJobCrawler implements PageProcessor {

    @Autowired
    private Pipeline jobInfoDaoPipeline;

    private static final String prefix = "https://www.lagou.com/jobs/list_";
    private static final String suffix = "?px=default&city=%E5%85%A8%E5%9B%BD#filterBox";


    private static final ArrayList keyWorkList =
            new ArrayList<String>(Arrays.asList("Java", "C++", "数据挖掘", "C", "PHP", "C#", "全栈工程师", ".NET", "Hadoop", "Python",
                    "H5", "Android", "iOS"));

    private int flag = 0;

    private Site site = Site.me().setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
            .addHeader("Accept-Encoding", "gzip, deflate, br")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
            .addHeader("Connection", "keep-alive")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Cookie", "user_trace_token=20180221192610-0cd990c1-271f-4700-a33a-9c85eaa0fe9e; _ga=GA1.2.1712211121.1519212371; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1519365533,1519397252,1519397722,1519397778; LGUID=20180221192611-04ec60d4-16fa-11e8-b085-5254005c3644; JSESSIONID=ABAAABAAAFCAAEG66812B56DE7FB5908686C90DAFDB097A; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1519436155; index_location_city=%E5%8C%97%E4%BA%AC; TG-TRACK-CODE=search_code; SEARCH_ID=7a8f8d7571e940d9bb3cc182a2768f0d; _gid=GA1.2.790796103.1519365535; LGRID=20180224093604-13da0c21-1903-11e8-b08f-5254005c3644; _gat=1; LGSID=20180224093604-13da0a8d-1903-11e8-b08f-5254005c3644; PRE_UTM=; PRE_HOST=; PRE_SITE=; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2Fjobs%2Flist_html5%3Fpx%3Ddefault%26city%3D%25E5%2585%25A8%25E5%259B%25BD")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0")
            .addHeader("Upgrade-Insecure-Requests", "1");

    private void crawl() {
        OOSpider.create(new LaGouJobCrawler())
                .addUrl("https://www.lagou.com/jobs/positionAjax.json?px=default&needAddtionalResult=false&isSchoolJob=0")
                .addPipeline(jobInfoDaoPipeline)
                .thread(5)
                .run();
    }

    public void process(Page page) {
        if (flag == 0) {
            //第一次进来把后续pagaUrl放入队列中
            processUrl(page);
            flag++;
        }
        System.out.println(page);
        /*else if (page.getUrl().toString().matches("https://www.lagou.com/jobs/list([\\s\\S]*?)")) {
            //从页面发现后续的url地址来抓取;
            List<String> hrefs = page.getHtml().$("div.p_top a.position_link", "href")
                    .regex("https://www.lagou.com/jobs/\\w+.html").all();
            page.addTargetRequests(hrefs);
        } else {
            page.putField("title", page.getHtml().xpath("//span[@class='name']/text()").toString());
            page.putField("salary", page.getHtml().xpath("//span[@class='salary']/text()").toString());
            page.putField("company", page.getHtml().xpath("//dl[@class='job_company']/dt/a/div/h2/text()").toString());
            page.putField("description", page.getHtml().xpath("//dd[@class='job_bt']/allText()").toString());
            page.putField("source", "https://www.lagou.com");
            page.putField("city", page.getHtml().xpath("//dd[@class='job_request']/p/span[2]/text()").toString());
            page.putField("educationRequire", page.getHtml().xpath("//dd[@class='job_request']/p/span[4]/text()").toString());
            page.putField("experience", page.getHtml().xpath("//dd[@class='job_request']/p/span[3]/text()").toString());
            page.putField("url", page.getUrl().toString());
            page.putField("urlmd5", DigestUtils.md5Hex(page.getUrl().toString()));
            page.putField("companySize", page.getHtml().xpath("//ul[@class='c_feature']/li[3]/text()").toString());
        }*/
        /*if (page.getUrl().toString().matches("https://www.lagou.com/jobs/list_([\\s\\S]*?)")) {
            //从页面发现后续的url地址来抓取;
            List<String> hrefs = page.getHtml().$("a", "href")
                    .regex("https://www.lagou.com/jobs/\\d+.html").all();
            page.addTargetRequests(hrefs);
        }else {
            page.putField("title", page.getHtml().xpath("//span[@class='name']/text()").toString());
            page.putField("salary", page.getHtml().xpath("//span[@class='salary']/text()").toString());
            page.putField("company", page.getHtml().xpath("//dl[@class='job_company']/dt/a/div/h2/text()").toString());
            page.putField("description", page.getHtml().xpath("//dd[@class='job_bt']/allText()").toString());
            page.putField("source", "https://www.lagou.com");
            page.putField("city", page.getHtml().xpath("//dd[@class='job_request']/p/span[2]/text()").toString());
            page.putField("educationRequire", page.getHtml().xpath("//dd[@class='job_request']/p/span[4]/text()").toString());
            page.putField("experience", page.getHtml().xpath("//dd[@class='job_request']/p/span[3]/text()").toString());
            page.putField("url", page.getUrl().toString());
            page.putField("urlmd5", DigestUtils.md5Hex(page.getUrl().toString()));
            page.putField("companySize", page.getHtml().xpath("//ul[@class='c_feature']/li[3]/text()").toString());
            //从页面发现后续的url地址来抓取;
            List<String> hrefs = page.getHtml().$("a", "href")
                    .regex("https://www.lagou.com/jobs/\\d+.html").all();
            page.addTargetRequests(hrefs);
        }*/
    }

    public Site getSite() {
        return site;
    }

    //使用post发送分页信息
    private void processUrl(Page page) {
        //提前把展示列表的url统一放入 可到30页 需要发送post请求
        Map<String, Object> map = new HashMap<String, Object>();
        //请求列表
        Request[] requests = new Request[3];
        for (int i = 0; i < requests.length; i++) {
            String url = "https://www.lagou.com/jobs/positionAjax.json?px=default&needAddtionalResult=false&isSchoolJob=0";
            requests[i] = new Request(url);
            requests[i].setMethod(HttpConstant.Method.POST);
            if (i == 0) {
                map.put("first", "true");
                map.put("pn", i + 1);
                map.put("kd", "java");
                try {
                    requests[i].setRequestBody(HttpRequestBody.form(map, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                page.addTargetRequest(requests[i]);
            } else {
                map.put("first", "false");
                map.put("pn", i + 1);
                map.put("kd", "java");
                try {
                    requests[i].setRequestBody(HttpRequestBody.form(map, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                page.addTargetRequest(requests[i]);
            }
        }
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final LaGouJobCrawler jobCrawler = applicationContext.getBean(LaGouJobCrawler.class);
        jobCrawler.crawl();
    }
}
