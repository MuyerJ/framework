package com.muyer.spider;

/**
 * Description: 
 * date: 2021/6/23 22:32
 * @author YeJiang
 * @version
 */
public class GaoKao {

    public static void main(String[] args) {
        Request request = new Request();
        request.setCharSet("UTF-8");
        String content = request.get("https://static-gkcx.eol.cn/www/2.0/json/samescore/9549/list.json").getContent();
        System.out.println(1);
    }
}
