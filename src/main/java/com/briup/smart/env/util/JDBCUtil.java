package com.briup.smart.env.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-22 14:09
 */
public class JDBCUtil {
    private static DataSource dataSource;
    static {
        try {
            InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            Properties prop = new Properties();
            prop.load(is);
            //使用工厂创建连接池对象
            dataSource= DruidDataSourceFactory.createDataSource(prop);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private JDBCUtil(){

    }

    /**
     * 从数据库连接池中获取连接对象
     * @return
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    /**
     * 关闭资源
     */
    public static void close(ResultSet rs, Statement st,Connection conn) throws SQLException {
        if (rs!=null) rs.close();
        if (st!=null) st.close();
        if (conn!=null) conn.close();
    }
    public static void close(Statement st,Connection conn) throws SQLException {
        if (st!=null) st.close();
        if (conn!=null) conn.close();
    }
}
