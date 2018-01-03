package cn.xukai.spark;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaixu on 2017/12/28.
 */
public class Demo1 {
    static String user = "root";
    static String pwd = "root123";
    static String driver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://127.0.0.1:3306/htom?useUnicode=true&characterEncoding=UTF-8";

    public static void main(String[] args) throws SQLException {
        Connection baseCon = null;
        String sqlStr = "insert into hly_temp_normal (stnid,month,day,value1,value2,value3,value4,value5,value6,value7,value8,value9,value10,value11,value12,value13,value14,value15,value16,value17,value18,value19,value20,value21,value22,value23,value24)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        List parasValues = new ArrayList();
        try {
            baseCon = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) { // TODO Auto-generated catch block e.printStackTrace();
        } // 替换为文件地址
        String allRowsStr = readFileByChars("d:\\TestZone\\hly-temp-normal.txt", "gbk");
        String[] rows = allRowsStr.split("\n");
        for (String row : rows) {
            parasValues.add(row.split("\\s+"));
        }
        PreparedStatement basePsm = null;
        try {
            baseCon.setAutoCommit(false);
            basePsm = baseCon.prepareStatement(sqlStr);
            for (int i = 0; i < parasValues.size();
                 i++) {
                Object[] parasValue = (Object[]) parasValues.get(i);
                for (int j = 0; j <
                        parasValue.length; j++) {
                    basePsm.setObject(j + 1, parasValue[j]);
                }
                basePsm.addBatch();
            }
            basePsm.executeBatch();
            baseCon.commit();
        } catch (SQLException e) {
            baseCon.rollback();
            throw e;
        } finally {
            if (basePsm != null) {
                basePsm.close();
                basePsm = null;
            }
            if (baseCon != null) {
                baseCon.close();
            }
        }
    }

    public static String readFileByChars(String fileName, String enc) {
        StringBuffer content = new StringBuffer();
        Reader reader = null;
        try { // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName), enc); // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) { // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
                    content.append(tempchars);
                } else {
                    for (int i = 0; i < charread;
                         i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            content.append(tempchars[i]);
                        }
                    }
                }
            }
            return content.toString();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

}
