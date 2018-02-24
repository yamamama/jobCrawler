package us.codecraft.jobhunter.crawler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class BossJobCrawler {



    public void crawl() {
//        OOSpider.create(Site.me()
//                .setSleepTime(2000)
//                .addCookie("sid","sem_pz_bdpc_index")
//                .addCookie("lastCity","100010000")
//                .addCookie("JSESSIONID","")
//                .addCookie("__g","sem_pz_bdpc_index")
//                .addCookie("__l","r=https://www.baidu.com/link?url=A-EVzwrBDV-RBmdu2xhoIn1sAM1CkOiXEKkzSWv4wd4409rJ7VhIWe-4PqmG9VwH&wd=&eqid=b64f604f0000d40b000000065a8ed82d&l=/www.zhipin.com/&g=/www.zhipin.com/?sid=sem_pz_bdpc_in")
//                .addCookie("__a","60324768.1519310897.1519310897.1519310902.14.2.13.14")
//                .addCookie("Hm_lvt_194df3105ad7148dcf2b98a91b5e727a","1519310900,1519310902")
//                .addCookie("Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a","1519312625")
//                .addCookie("__c","1519310902")
//                .addCookie("t","yPxu7afBThJJ43hs")
//                .addCookie("wt","yPxu7afBThJJ43hs")
//                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0"),jobInfoDaoPipeline, BossJobInfo.class)
//                .addUrl("https://www.zhipin.com/job_detail")
//                .thread(1)
//                .run();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final BossJobCrawler jobCrawler = applicationContext.getBean(BossJobCrawler.class);
        jobCrawler.crawl();
    }
}
