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
public class LiePinJobCrawler implements PageProcessor {
    @Autowired
    private Pipeline jobInfoDaoPipeline;
    //010 计算机软件 030 it服务 420 网络游戏 040 互联网电商
    private static final String prefix = "https://www.liepin.com/zhaopin/?industries=010&curPage=";

    private Site site = Site.me().setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("Accept","*/*")
            .addHeader("Accept-Encoding","gzip, deflate, br")
            .addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
            .addHeader("Connection","keep-alive")
            .addHeader("Cookie","abtest=0; _fecdn_=1; gr_user_id=b99bcebe-7e8a-4c14-96b7-d544ca576325; __uuid=1519289344986.68; __tlog=1519289344986.20%7C00000000%7CR000000075%7Cs_o_001%7Cs_o_001; __session_seq=12; Hm_lvt_a2647413544f5a04f00da7eee0d5e200=1519289345,1519365528; Hm_lpvt_a2647413544f5a04f00da7eee0d5e200=1519375682; verifycode=2194a9e350514080b0e588289cbe7384; firsIn=1; __uv_seq=7; gr_session_id_bad1b2d9162fab1f80dde1897f7a2972=bdea1dc6-b829-4de2-9ba3-d09947363375; 10cd53a=6158430ca8bbda180159ce11cff56da8; user_name=%E6%9B%BE%E5%89%91%E9%94%8B; lt_auth=uu4KOHcFxl3%2Bt3jciDFatapJ2dn5UGnN93QN0UhTita0Xqbl4PrjRg2CqbcDxBMhkksmJsULNLH3%0D%0ANu%2F7z3FN4kMbwG6niZ%2B0veW7138eSuVcI%2Fvz0Kn3lc3YS80mwC1RwnBh8C0eyRqhs0EjZI6%2BwF3I%0D%0Ap6HH7ral8vvE%0D%0A; UniqueKey=686e83115fa58bb11a667e00ad0d9493; user_kind=0; user_photo=55557f3b28ee44a8919620ce01a.gif; is_lp_user=true; user_vip=0; c_flag=e389fc1ec3b628232a2bee6ba83bced4; new_user=false; need_bind_tel=false; gr_cs1_bdea1dc6-b829-4de2-9ba3-d09947363375=UniqueKey%3A686e83115fa58bb11a667e00ad0d9493; slide_goldcard_times20180223=3; login_temp=islogin; socketConnectStore=%7B%22pageId%22%3A%22webim_pageid_538166946763.8638%22%2C%22connectDomain%22%3A%22liepin.com%22%2C%22socketConnect%22%3A%221%22%7D")
            .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0");



    public void crawl() {
        //提前把展示列表的url统一放入 猎聘从0开始
        List<String> urls = new ArrayList<String>();
        for (int i = 0; i <= 1; i++) {
            String url = prefix+i;
            urls.add(url);
        }
        OOSpider.create(new LiePinJobCrawler())
                .startUrls(urls)
                .addPipeline(jobInfoDaoPipeline)
                .thread(5)
                .run();
    }

    public void process(Page page) {
        if (page.getUrl().toString().matches("https://www.liepin.com/zhaopin/([\\s\\S]*?)")){
            //从页面发现后续的url地址来抓取;
            List<String> hrefs = page.getHtml().$("div.job-info h3 a", "href")
                    .regex("https://www.liepin.com/job/\\w+.shtml").all();
            page.addTargetRequests(hrefs);
        }else {
            page.putField("title", page.getHtml().xpath("//h1/text()").toString());
            page.putField("salary", page.getHtml().xpath("//p[@class='job-item-title']/text()").toString());
            page.putField("company", page.getHtml().xpath("//div[@class='title-info']/h3/a/text()").toString());
            page.putField("description", page.getHtml().xpath("//div[@class='content content-word']/allText()").toString());
            page.putField("source", "http://www.liepin.com");
            page.putField("city", page.getHtml().xpath("//p[@class='basic-infor']/span/a/text()").toString());
            page.putField("educationRequire", page.getHtml().xpath("//div[@class='job-qualifications']/span[1]/text()").toString());
            page.putField("experience", page.getHtml().xpath("//div[@class='job-qualifications']/span[2]/text()").toString());
            page.putField("url",page.getUrl().toString() );
            page.putField("urlmd5", DigestUtils.md5Hex(page.getUrl().toString()) );
            page.putField("companySize",page.getHtml().xpath("//ul[@class='new-compintro']/li[2]/text()").toString().split("：")[1]);
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final LiePinJobCrawler jobCrawler = applicationContext.getBean(LiePinJobCrawler.class);
        jobCrawler.crawl();
    }
}
