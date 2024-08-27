package com.briup.smart.env.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-19 16:42
 */
public class dataSourceUtils {
    public static Connection createDataSource() throws Exception {
        Properties prop = new Properties();
        InputStream is = dataSourceUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        prop.load(is);
        //1.创建一个数据库连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        //2.获取10个数据库连接对象conn
        Connection conn = dataSource.getConnection();
        return conn;
    }
}
