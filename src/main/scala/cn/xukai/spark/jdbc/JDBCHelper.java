package cn.xukai.spark.jdbc;

import cn.xukai.spark.ansj.AnsjUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2017/8/24.
 */
public class JDBCHelper {

    private static BasicDataSource ds;
    static {
        Properties properties=new Properties();
        try {
            properties.load(JDBCHelper.class.getClassLoader().getResourceAsStream("dbcp.properties"));
            System.out.println(properties.getProperty("username"));
            //创建连接池对象，并且用连接池工厂来加载配置对象的信息
            ds = (BasicDataSource) BasicDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 用于查询，返回结果集
     * @param sql sql语句
     * @return 结果集
     * @throws SQLException
     */
    public static List<BaiduNewsResult> query(String sql) throws SQLException {
        List<BaiduNewsResult> list = new ArrayList<>();
        Connection conn=null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            conn=ds.getConnection();
            preparedStatement=conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                BaiduNewsResult bns=new BaiduNewsResult();
                bns.setContent(rs.getString("content"));
                bns.setId(rs.getLong("id"));
                bns.setLabel(rs.getString("label"));
                list.add(bns);
            }
            return list;
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            if(rs!=null)
                rs.close();
            if(preparedStatement!=null )
                preparedStatement.clearParameters();
            if(conn!=null)
                conn.close();
        }
    }
    /**
     * 用于查询，返回结果集
     * @param sql sql语句
     * @return 结果集
     * @throws SQLException
     */
    public static <T> Integer save(String sql,List<T> lis ) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            Savepoint sp = conn.setSavepoint();
            if (lis.size() != 0 && lis.get(0) instanceof BaiduNewsResult) {
                List<BaiduNewsResult> list = (List<BaiduNewsResult>) lis;
                preparedStatement = conn.prepareStatement(sql);
                int i = 0;
                for (BaiduNewsResult news : list) {
                    i++;
                    preparedStatement.setString(1, news.getLabel());
                    preparedStatement.setLong(2, news.getId());
                    preparedStatement.addBatch();
                    if (i % 50 == 0) {
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                    }
                }
            }
            preparedStatement.executeBatch();
            conn.commit();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("请注意---:更新数据库失败了！！！");
            return -1;
        } finally {

            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (conn != null)
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }

    public static void main(String[] args) throws SQLException {
        List<BaiduNewsResult> baiduNewsResults = JDBCHelper.query("select * from test");
        for(BaiduNewsResult baiduNewsResult :baiduNewsResults){
            AnsjUtils.getFC(baiduNewsResult);
        }
    }
}


