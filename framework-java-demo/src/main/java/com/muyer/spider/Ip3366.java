package com.muyer.spider;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *      代理地址：http://www.ip3366.net/free/
 * date: 2020/10/11 23:43<br/>
 *
 * @author MuyerJ<br />
 */
public class Ip3366 {
    public static void main(String[] args) {
        List<Proxy> proxyList = new ArrayList<>();
        String baseUrl = "http://www.ip3366.net/free/";
        List<String> types = Arrays.asList("stype=1", "?stype=2");
        int pageMax = 7;
        for (String type : types) {

            for (int i = 1; i <= pageMax; i++) {
                String url = baseUrl + "?" + type + "&page=" + i;
                Request request = new Request();
                String content = request.get(url).getContent();
                Elements es = Jsoup.parse(content).select("#list table tbody tr");
                es.forEach(element -> {
                    Elements proxyElement = element.select("td");
                    String ip = proxyElement.get(0).text();
                    int port = Integer.parseInt(proxyElement.get(1).text());
                    Proxy proxy = new Proxy(ip, port);
                    proxyList.add(proxy);
                    System.out.println(proxy);
                });
            }
        }

    }
}
