package com.muyer.io;

import com.alibaba.dubbo.common.utils.StringUtils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 
 * date: 2021/3/6 16:53
 * @author YeJiang
 * @version
 */
public class IODemo {

    static String insertPrefix = "INSERT INTO `log`(`token`, `time`, `duration`, `type`) VALUES ('";
    static Writer writer;
    //匹配前后字符，提前中间
    static Pattern p1 = Pattern.compile("(?<=\\(token=).+?(?=, app_ver=)");

    static Pattern pattern = Pattern.compile(", orderNo=OR\\d*");
    //匹配后面字符，提取前面
    static Pattern p2 = Pattern.compile(".+?(?=\\|INFO)");
    static Pattern p3 = Pattern.compile("(?<=\\|)\\d+?(?=\\|)");

    static {
        File file = new File("F:" + File.separator + "updateSql.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加true是追加，不加true就是覆盖了
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception {
        createUpdateSqlFile();
    }

    /**
     *   INSERT INTO `log`(`token`, `time`, `duration`, `type`) VALUES ('2', 3, 4, 5)
     *   token|抢单时间|抢单时长|浏览订单|抢订单
     *   contains:
     *      k1:/driver/grab/v2/grab-order/aggregation
     *      k2:/api/driver/grab/start-grab-order
     *   正则取固定的字符串：
     *   (token=cc9d9f8dbb5b2867a74c501ae946d851, app_ver=
     *   2021-06-10 15:08:27.016|INFO
     *   |309|
     * @throws IOException
     */
    private static void getGrabOrderInfo() throws IOException {
        String fileName = "未命名.txt";
//        String fileName = "抢单日志.txt";
        Reader reader =
                new FileReader("C:\\Users\\dell\\Documents\\WeChat Files\\wxid_z79lcgwgw4aa22\\FileStorage\\File\\2021-06\\" + fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr;
        while ((lineStr = bufferedReader.readLine()) != null) {
            System.out.println(lineStr);
            if (!lineStr.contains("combinationOrderNo=CO")) {
                continue;
            }
            if (!lineStr.contains("/api/driver/grab/start-grab-order")) {
                continue;
            }
//            if (!lineStr.contains("orderNo=OR00")) {
//                continue;
//            }
            String t = getTime(lineStr);
            if (StringUtils.isEmpty(t)) {
                continue;
            }
            String type = getType(lineStr);
            if ("0".equals(type)) {
                continue;
            }
            String time = t.split("\\.")[0].replace(" ", "")
                    .replace(":", "")
                    .replace("-", "");
//            String s = insertPrefix + getToken(lineStr) + "','" + time + "','" + getDuration(lineStr) + "','" + type + "');";
            String s = getToken(lineStr) + " " + time + " " + getDuration(lineStr) + " " + type;
            //写数据
            writer.write(s + "\r\n");
        }

        bufferedReader.close();
        reader.close();


        writer.close();
    }

    private static String getType(String ags) {
        if (ags.contains("/driver/grab/v2/grab-order/aggregation")) {
            return "1";
        } else if (ags.contains("/api/driver/grab/start-grab-order")) {
            return "2";
        } else {
            return "0";
        }
    }

    private static String getDuration(String ags) {
        Matcher matcher = p3.matcher(ags);
        while (matcher.find()) {
            return matcher.group().trim();
        }
        return "";
    }


    private static String getTime(String ags) {
        Matcher matcher = p2.matcher(ags);
        while (matcher.find()) {
            return matcher.group().trim();
        }
        return "";
    }

    private static String getToken(String ags) {
        Matcher matcher = p1.matcher(ags);
        while (matcher.find()) {
            return matcher.group().trim();
        }
        return "";
    }


    private static void readFile() throws IOException {
        //读数据
        Reader reader = new FileReader("F:" + File.separator + "new 2.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr = "";
        String resultStr = null;
        int i = 0;
        while ((lineStr = bufferedReader.readLine()) != null) {
            Matcher matcher = pattern.matcher(lineStr);
            String value;
            while (matcher.find()) {
                value = matcher.group().replace(", ", "").trim();
                resultStr = resultStr + value.split("=")[1] + "\r\n";
                i++;
            }
        }
        bufferedReader.close();
        reader.close();

        System.out.println(i);

        //写数据
        File file = new File("F:" + File.separator + "out3.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        //加true是追加，不加true就是覆盖了
        Writer writer = new FileWriter(file, true);
        writer.write(resultStr);
        writer.close();
    }

    public static void createUpdateSqlFile() throws Exception {
        //读数据
        Reader reader = new FileReader("F:" + File.separator + "sql.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr = "";
        String resultStr = "";
        while ((lineStr = bufferedReader.readLine()) != null) {
            String[] values = lineStr.split("VALUES")[1]
                    .replace("(", "")
                    .replace(")", "").trim().split(",");
            resultStr = resultStr + "update `dict_area` set lng = " + values[values.length - 2].trim()
                    + " , lat =  " + values[values.length - 1].trim() + " where area_id = " + values[0].trim() + " ; " + "\r\n";
        }
        bufferedReader.close();
        reader.close();
        //写数据
        File file = new File("F:" + File.separator + "updateSql.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        //加true是追加，不加true就是覆盖了
        Writer writer = new FileWriter(file, true);
        writer.write(resultStr);
        writer.close();
    }

}
