package com.briup.smart.env.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-19 14:28
 */
public class TestJDBC2 {
    private Connection conn;
    @Before
    public void getConnection() throws Exception {
        //初始化statement对象
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接对象
        String url="jdbc:mysql://127.0.0.1:3306/briup_db?useServerPrepStmts=true";
        conn = DriverManager.getConnection(url, "root", "1234");
        //System.out.println("连接对象："+conn);
    }
    @Test
    public void method1() throws Exception {
        //创建添加学生对象的集合
        Student s1 = new Student(1, "jack");
        Student s2 = new Student(2, "tom");
        Student s3 = new Student(3, "bob");
        List<Student> list = Arrays.asList(s1, s2, s3);
        String sql="insert into s_course values (?,?,1,'高等数学')";
        //获取ps预编译对象
        PreparedStatement ps = conn.prepareStatement(sql);
        list.stream().forEach(s->{
            try {
                ps.setInt(1,s.getId());
                ps.setString(2,s.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        ps.execute();
    }
    @Test
    public void method2(){
        //1.获取statement对象

    }
    @Test
    public void method3(){

    }
    @Test
    public void method4(){

    }
    @Test
    public void method5(){

    }
    @After
    public void closeConnection() throws SQLException {
        //关闭资源
        if (conn!=null) conn.close();
    }

}
