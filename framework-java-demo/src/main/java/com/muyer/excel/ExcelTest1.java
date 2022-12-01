package com.muyer.excel;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description:
 * date: 2021/1/4 10:56
 *
 * @author YeJiang
 */
public class ExcelTest1 {
    public static void main(String[] args) throws Exception {
        fun2();
    }

    private static void fun2() {
        List<ExcelDto2> excel = EasyExcel.read(new File("E:\\交易记录.xlsx")).autoTrim(true).head(ExcelDto2.class).sheet().doReadSync();
        Set<String> s1 = excel.stream().map(ExcelDto2::getX1).collect(Collectors.toSet());
        Set<String> s2 = excel.stream().map(ExcelDto2::getX2).collect(Collectors.toSet());
        for(String s :s2){
            if(s1.contains(s)){
                continue;
            }
            System.out.println(s);
        }
    }

    public static void excelAdd2() throws Exception {
        File file = new File("E:" + File.separator + "updateCity2.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream finalOutputStream = new FileOutputStream(file, true);
        List<ExcelDto> excel = EasyExcel.read(new File("E:\\济宁匹配费用模板1.xlsx")).autoTrim(true).head(ExcelDto.class).sheet().doReadSync();
        String str = "update `t_receive_truck_network` set end_city = ";
        excel.forEach(a -> {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("'" + a.getEnd_city() + "'  ");
            sb.append("where addr_code = ");
            sb.append("'" + a.getAddr_code() + "'");
            sb.append(";\n");
            try {
                finalOutputStream.write(sb.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        finalOutputStream.close();

    }
}
