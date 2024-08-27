package com.briup.smart.env.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-19 9:48
 */
public class TestJDBC {
    private Statement st;
    private Connection conn;
    private List<Student> list;
    //会执行这个构造代码块，说明会创建该类的对象
    {
        //将集合中的学生信息保存到数据库中
        Student s1 = new Student(1, "jack");
        Student s2 = new Student(2, "bob");
        Student s3 = new Student(3, "mike");
        list = Arrays.asList(s1, s2, s3);
    }
    @Test
    public void insert() throws Exception {
        //4.执行的sql语句
        String sql="insert into s_course values (10,'tom10',1,'高等数学')";
        st.execute(sql);
    }
    @Test
    public void select() throws SQLException {
        //执行sql的语句
        String sql="select id,name from s_course";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            //获取对应的列名如果是id as uid就写uid这个别名
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println(id+"--"+name);
        }
    }
    @Test
    public void insert2() throws SQLException {
        //获取预编译对象prepareStatement ps
        String sql="insert into s_course values (?,?,1,'高等数学')";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (Student stu : list) {
            //设置值
            ps.setInt(1,stu.getId());
            ps.setString(2,stu.getName());
            ps.execute();
        }
    }
    //批处理
    @Test
    public void insert3() throws SQLException {
        //1.注册驱动
        //2.获取连接
        //3.获取预编译ps对象
        String sql="insert into s_course values (?,?,1,'高等数学')";
        PreparedStatement ps = conn.prepareStatement(sql);
        //4.执行批处理
        list.stream().forEach(s -> {
            try {
                //设置值
                ps.setInt(1,s.getId());
                ps.setString(2,s.getName());
                //添加到批处理中
                ps.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        //统一进行批处理的执行
        int[] arr = ps.executeBatch();
        System.out.println(Arrays.toString(arr));
    }
    @Test
    public void insert4() throws SQLException {
        //将事务的自动提交改成手动提交
        conn.setAutoCommit(false);
        //获取预编译对象prepareStatement ps
        String sql="insert into s_course values (?,?,1,'高等数学')";
        PreparedStatement ps = conn.prepareStatement(sql);
        int num=0;
        for (Student stu : list) {
/*            if (num==1){
                throw new RuntimeException("模拟异常");
            }*/
            //设置值
            ps.setInt(1,stu.getId());
            ps.setString(2,stu.getName());
            ps.execute();
            num++;
        }
        //手动提交事务
        conn.commit();
    }
    @Test
    public void insert5() throws SQLException {
        //1.注册驱动
        //2.获取连接对象
        //3.设置事务为手动提交
        conn.setAutoCommit(false);

    }
    @Before
    public void initStatement() throws ClassNotFoundException, SQLException {
        //初始化statement对象
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接对象
        String url="jdbc:mysql://127.0.0.1:3306/briup_db?useServerPrepStmts=true";
        conn = DriverManager.getConnection(url, "root", "1234");
        //System.out.println("连接对象："+conn);
        //3.获取statement对象
        st = conn.createStatement();
    }
    @After
    public void close() throws SQLException {
        //关闭资源
        if (st!=null) st.close();
        if (conn!=null) conn.close();
    }

}
