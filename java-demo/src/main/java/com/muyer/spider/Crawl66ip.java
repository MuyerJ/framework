package com.muyer.spider;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * date: 2021/1/5 18:53
 *
 * @author YeJiang
 */
public class Crawl66ip {
    public static void main(String[] args) {
        List<Proxy> proxyList = new ArrayList<>();
        String baseUrl = "http://www.66ip.cn/";
        //获取总页数
        Request requestGetPageNum = new Request();
        String content = requestGetPageNum.get(baseUrl).getContent();
        Elements es = Jsoup.parse(content).select(".containerbox .mypage #PageList a");
        int pageMax = Integer.parseInt(es.get(es.size() - 2).text());
        System.out.println("共" + pageMax + "页");
        for (int i = 0; i < pageMax; i++) {
            System.out.println("第" + i + "页");
            Request request = new Request();
            String ipTable = request.get(baseUrl + i + ".html").getContent();
            Elements ips = Jsoup.parse(ipTable).select("#main .containerbox [align=center] table tbody tr");
            if (ips.size() <= 1) {
                continue;
            }
            //跳过第一个tr（表头）
            for (int j = 1; j < ips.size(); j++) {
                Elements proxyElement = ips.get(j).select("td");
                String ip = proxyElement.get(0).text();
                int port = Integer.parseInt(proxyElement.get(1).text());
                Proxy proxy = new Proxy(ip, port);
                proxyList.add(proxy);
                System.out.println(proxy);
            }
        }
        System.out.println(proxyList.size());
    }
}
