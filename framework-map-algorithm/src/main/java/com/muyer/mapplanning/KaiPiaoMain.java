package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.muyer.mapplanning.excel.GasStationDTO;
import com.muyer.mapplanning.excel.KaiPiaoDTO;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;

/**
 * Description: 
 * date: 2022/4/5 12:32
 * @author YeJiang
 * @version
 */
public class KaiPiaoMain {
    static Writer writer;

    static {
        File file = new File("F:" + File.separator + "updateSql20220525.sql");
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
        String pre = "update `t_waybill` w , `t_main_order`  m set  w.`invoice_status` = 5 WHERE  w.`order_no` = m.`order_no` and w.`third_order_no`  = '%s' and m.`order_no` = '%s';";

        List<KaiPiaoDTO> list = EasyExcel.read(new File("C:\\Users\\dell\\Documents\\WeChat Files\\wxid_z79lcgwgw4aa22\\FileStorage\\File\\2022-05\\已开票20220525.xlsx")).autoTrim(true)
                .head(KaiPiaoDTO.class).sheet().doReadSync();

        for (KaiPiaoDTO dto : list) {
            if (StringUtils.isEmpty(dto.getTag()) && StringUtils.isEmpty(dto.getOrderNo())) {
                continue;
            }
            //写数据
            //System.out.println(String.format(pre, dto.getTag(), dto.getOrderNo()));
            writer.write(String.format(pre, dto.getTag(), dto.getOrderNo()) + "\r\n");
        }
        writer.close();
    }
}
