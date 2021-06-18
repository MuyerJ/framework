package com.muyer.entity;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DaoCodeGenerateUtils {

    public static final String tableName = EntityCodeGenerateUtils.tableName;
    //数据库连接
    private static final String URL = EntityCodeGenerateUtils.URL;
    private static final String NAME = EntityCodeGenerateUtils.NAME;
    private static final String PASSWORD = EntityCodeGenerateUtils.PASSWORD;
    private static final String DRIVER = EntityCodeGenerateUtils.DRIVER;
    private static final String dbName = EntityCodeGenerateUtils.dbName;
    private static String moduleName;
    private String tablename;


    public DaoCodeGenerateUtils(Connection con, String tableName, String packageOutPath, String daoSuffix, String entitySuffix) {

        tablename = tableName;
        try {
            String content = parse(packageOutPath, daoSuffix, entitySuffix);
            try {
                File directory = new File("");
                String outputPath =
                        directory.getAbsolutePath() + moduleName + "/src/main/java/" + packageOutPath.replace(".", "/") + "/"
                                + parseName(tableName, daoSuffix) + ".java";
                if (directory.exists()) {
                    return;
                }

                FileWriter fw = new FileWriter(outputPath);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //指定实体生成所在包的路径
        String packageOutPath = "com.muyer.repository";
        String daoSuffix = "Dao";
        String entitySuffix = "Entity";
        moduleName = "/springboot-security";
        generateEntityCode(tableName, packageOutPath, daoSuffix, entitySuffix);
    }

    public static void generateEntityCode(String tableName, String packageOutPath, String daoSuffix, String entitySuffix) {
        List<String> tableList = new ArrayList<>();
        Connection con;
        try {
            con = getConnection();
            DatabaseMetaData dm = con.getMetaData();
            ResultSet rs = dm.getTables(con.getCatalog(), "root", null, new String[]{"TABLE"});
            while (rs.next()) {
                tableList.add(rs.getString("TABLE_NAME"));
            }

            if (StringUtils.isNotBlank(tableName)) {
                new DaoCodeGenerateUtils(con, tableName, packageOutPath, daoSuffix, entitySuffix);
                return;
            }

            if (!CollectionUtils.isEmpty(tableList)) {
                for (String table : tableList) {
                    new DaoCodeGenerateUtils(con, table, packageOutPath, daoSuffix, entitySuffix);
                }
            }
        } catch (Exception e1) {
            log.error("", e1);
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL + "/" + dbName, NAME, PASSWORD);
        } catch (Exception e) {
            log.error("get connection failure", e);
        }
        return conn;
    }

    private String parse(String packageOutPath, String daoSuffix, String entitySuffix) {
        StringBuffer sb = new StringBuffer();

        sb.append("package " + packageOutPath + ";\r\n");
        sb.append("\r\n");
        sb.append("import org.springframework.data.jpa.repository.JpaRepository;\r\n");
        sb.append("import org.springframework.stereotype.Repository;\r\n");
        sb.append("\r\n");
        //实体部分
        sb.append("@Repository\r\n");
        sb.append("public interface " + parseName(tablename, daoSuffix) + " extends JpaRepository<" + parseName(tablename,
                entitySuffix)+",Long" + "> {\r\n");

        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * 描述：全部首字母大写
     * @param name
     * @return
     * String
     */
    public String parseName(String name, String suffix) {
        StringBuffer sb = new StringBuffer();
        String[] array = name.split("_");
        if (array.length > 0) {
            for (String string : array) {
                sb.append(initcap(string));
            }
        }

        return sb.toString().substring(1) + suffix;
    }

    /**
     * 功能：将输入字符串的首字母改成大写（修改成驼峰法）
     * @param str
     * @return
     */
    private String initcap(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }
}
