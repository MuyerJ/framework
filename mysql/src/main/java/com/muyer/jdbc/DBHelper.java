package com.muyer.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Description: 
 * date: 2021/9/7 22:11
 * @author YeJiang
 * @version
 */
public class DBHelper {

    static String URL = "jdbc:mysql://127.0.0.1:3306/xxl_job?characterEncoding=utf-8";
    static String USER = "root";
    static String PASSWORD = "123456";

    public static void main(String[] args) {
        try {
            //1、加载driver
            Class.forName("com.mysql.jdbc.Driver");
            //2、获取DB Connection
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //3、操作
            String sql = "select * from xxl_job_log";
            //3.1、预编译
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            //4、结果
            while (rs.next()){
                System.out.println(rs.getString("job_group")+","+rs.getString("job_id")+","+rs.getString("trigger_time"));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
