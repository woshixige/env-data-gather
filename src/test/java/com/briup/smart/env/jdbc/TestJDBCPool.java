package com.briup.smart.env.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-19 15:16
 */
public class TestJDBCPool {
    @Test
    public void readProperties() throws IOException {
        //java默认配置文件
        Properties prop = new Properties();
        //通过反射获取类加载器对象
        ClassLoader classLoader = this.getClass().getClassLoader();
        //classes 字节码文件下这其实是一个路径
        InputStream is = classLoader.getResourceAsStream("druid.properties");
        prop.load(is);
        String url = prop.getProperty("url");
        System.out.println(url);
        /*//继承HashTable Map的实现类
        prop.setProperty("id","1");
        System.out.println(prop.getProperty("id"));*/
    }
    @Test
    public void createDataSource() throws Exception {
        Properties prop = new Properties();
        InputStream is = TestJDBCPool.class.getClassLoader().getResourceAsStream("druid.properties");
        prop.load(is);
        //1.创建一个数据库连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
/*        //2.获取10个数据库连接对象conn
        Connection conn=null;
        for (int i = 0; i < 10; i++) {
            conn = dataSource.getConnection();
            System.out.println(conn);
        }*/
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        String sql="INSERT INTO s_region VALUES(?,?)";
        //3.获取st ps
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,7);
        ps.setString(2,"我是7");
        ps.addBatch();
        ps.executeBatch();
        conn.commit();
        //关闭连接，会把连接对象放回连接池中，并不会销毁这个对象
        if (conn!=null) conn.close();

    }
}
