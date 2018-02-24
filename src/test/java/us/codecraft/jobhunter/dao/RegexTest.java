package us.codecraft.jobhunter.dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        String txt="http://jobs.zhaopin.com/491025085250132.htm?ssidkey=y&ss=201&ff=03&sg=b3c21515cd5e4701b8071a6126fd1da5&so=1&uid=707409863";
        String txt2 = "城市：广州经验：3-5年学历：本科";

        String re1="http://sou.zhaopin.com/([\\s\\S]*?)";	// Non-greedy match on filler
        String re2="(?:[a-z][a-z]+)";	// Uninteresting: word
        String re3=".*?";	// Non-greedy match on filler
        String re4="(?:[a-z][a-z]+)";	// Uninteresting: word
        String re5=".*?";	// Non-greedy match on filler
        String re6="(?:[a-z][a-z]+)";	// Uninteresting: word
        String re7=".*?";	// Non-greedy match on filler
        String re8="((?:[a-z][a-z]+))";	// Word 1
        String re9=".*?";	// Non-greedy match on filler

        Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(txt);
        System.out.println(txt.matches(re1));

    }
}
