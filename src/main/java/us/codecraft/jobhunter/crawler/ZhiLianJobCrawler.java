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
public class ZhiLianJobCrawler implements PageProcessor{

    @Autowired
    private Pipeline jobInfoDaoPipeline;

    private static final String prefix = "http://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000&p=";

    private Site site = Site.me().setRetryTimes(3)
           .setSleepTime(1000)
           .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
           .addHeader("Accept-Encoding","gzip, deflate")
           .addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
           .addHeader("Connection","keep-alive")
           .addHeader("Cache-Control","no-cache")
           .addHeader("Cookie","dywea=95841923.3527025488270392000.1519349729.1519361708.1519352652.5; dywec=95841923; dywez=95841923.1519349729.1.1.dywecsr=(direct)|dyweccn=(direct)|dywecmd=(none)|dywectr=undefined; urlfrom=121126445; urlfrom2=121126445; adfcid=none; adfcid2=none; adfbid=0; adfbid2=0; _jzqa=1.1167653130219246300.1519349736.1519358480.1519361708.4; _jzqc=1; _jzqckmp=1; Hm_lvt_38ba284938d5eddca645bb5e02a02006=1519349736; Hm_lpvt_38ba284938d5eddca645bb5e02a02006=1519361753; _qzja=1.877159142.1519349736809.1519349736809.1519349736810.1519349736809.1519349736810..0.0.1.1; _qzjc=1; _qzjto=1.1.0; __utma=269921210.2014133017.1519349739.1519361711.1519352654.5; __utmc=269921210; __utmz=269921210.1519349739.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); stayTimeCookie=1519361933314; referrerUrl=http%3A//jobs.zhaopin.com/CC339817533J00033026302.htm; hiddenEpinDiv=none; lastchannelurl=http%3A//jobs.zhaopin.com/646673622250050.htm; qrcodekey=bff2ece6ee62400d93f531f60814c222; firstchannelurl=https%3A//passport.zhaopin.com/account/login%3FbkUrl%3Dhttp%253A%252F%252Fjobs.zhaopin.com%252F646673622250050.htm; JsNewlogin=2122229591; JSloginnamecookie=13247318065; JSShowname=%e6%9b%be%e5%89%91%e9%94%8b; at=f857cb6722bc4ae6ac64fe0f81cfa687; Token=f857cb6722bc4ae6ac64fe0f81cfa687; rt=3925b702bf0d45aa85a1a343aa971127; JSpUserInfo=3d753d6857645f7549685d645c754968536450754f6859645375356824645575486859645a754d685d645b754868526458754f685f6453752c6824645575870e3b3663e042682f6425754468106410751a680b6406751e682a6407750c681e640475166805640375576809640775146851643b752d685764597542682b643c7544685b6445754c684a64597549685064597540685164297535685764597542683f64297544682064247549685d645c754968536450754f6859645a7542683f643c7544685b6453752a6823645575486851643d75296824645575036812640b75186804640f75396805641d750d68066407751668016446751a680564057542683; uiioit=377220665963536655675366516452725d665c63526656675f6627642672596654635f660; usermob=55645573566647664464556745685C645673526646660; userphoto=; userwork=4; bindmob=1; rinfo=JL074098632R90500000000_1; monitorlogin=Y; nTalk_CACHE_DATA={uid:kf_9051_ISME9754_707409863,tid:1519352385626222}; NTKF_T2D_CLIENTID=guest9C94A73A-ABB2-712B-F4EC-C075905A1EEB; JSweixinNum=1; LastCity=%e5%85%a8%e5%9b%bd; LastCity%5Fid=489; loginreleased=1; JSSearchModel=0; LastSearchHistory=%7b%22Id%22%3a%22b8a9cecc-e5a6-4419-9322-cd21d97e444e%22%2c%22Name%22%3a%22%e5%85%a8%e5%9b%bd+%2b+%e8%bd%af%e4%bb%b6%2f%e4%ba%92%e8%81%94%e7%bd%91%e5%bc%80%e5%8f%91%2f%e7%b3%bb%e7%bb%9f%e9%9b%86%e6%88%90%22%2c%22SearchUrl%22%3a%22http%3a%2f%2fsou.zhaopin.com%2fjobs%2fsearchresult.ashx%3fbj%3d160000%22%2c%22SaveTime%22%3a%22%5c%2fDate(1519358484702%2b0800)%5c%2f%22%7d; bdshare_firstime=1519353433994")
           .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0")
           .addHeader("Upgrade-Insecure-Requests","1").setCharset("UTF-8");

    public void crawl() {
        //提前把展示列表的url统一放入
        List<String> urls = new ArrayList<String>();
        for (int i = 1; i <= 1; i++) {
            String url = prefix+i;
            urls.add(url);
        }

        OOSpider.create(new ZhiLianJobCrawler())
                .startUrls(urls)
                .addPipeline(jobInfoDaoPipeline)
                .thread(5)
                .run();
    }



    public void process(Page page) {
        if (page.getUrl().toString().matches("http://sou.zhaopin.com/([\\s\\S]*?)")){
            //从页面发现后续的url地址来抓取;
            List<String> hrefs = page.getHtml().$("table.newlist td.zwmc div a", "href")
                    .regex("http://jobs.zhaopin.com/\\w+.htm").all();
            page.addTargetRequests(hrefs);
        }else {
            page.putField("title", page.getHtml().xpath("//div[@class='fl']/h1/text()").toString());
            page.putField("salary", page.getHtml().xpath("//ul[@class='terminal-ul']/li/strong/text()").toString());
            page.putField("company", page.getHtml().xpath("//div[@class='fl']/h2/a/text()").toString());
            page.putField("description", page.getHtml().xpath("//div[@class='tab-inner-cont']/allText()").toString());
            page.putField("source", "http://jobs.zhaopin.com");
            page.putField("city", page.getHtml().xpath("//ul[@class='terminal-ul']/li[2]/strong/allText()").toString());
            page.putField("educationRequire", page.getHtml().xpath("//ul[@class='terminal-ul']/li[6]/strong/text()").toString());
            page.putField("experience", page.getHtml().xpath("//ul[@class='terminal-ul']/li[5]/strong/text()").toString());
            page.putField("url",page.getUrl().toString() );
            page.putField("urlmd5", DigestUtils.md5Hex(page.getUrl().toString()) );
            page.putField("companySize",page.getHtml().xpath("//div[@class='company-box']/ul/li/strong/text()").toString());
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final ZhiLianJobCrawler jobCrawler = applicationContext.getBean(ZhiLianJobCrawler.class);
        jobCrawler.crawl();
    }
}
