package com.briup.smart.env.jdbc;

import com.briup.smart.env.client.GatherImpl;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.util.dataSourceUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-20 10:14
 */
public class TestData {
    private PreparedStatement ps1;
    @Test
    public void method() throws Exception {
        //获取采集到的数据
        GatherImpl gather = new GatherImpl();
        Collection<Environment> coll = gather.gather();
        coll.forEach(e->{
            //获取集合中的日期属性值中的天 gatherDate=2018-01-19 08:59:56.029
            Timestamp date = e.getGatherDate();
            String[] split = date.toString().split("[ ]");
            String[] split1 = split[0].split("-");
            String day = split1[2];
            try {
                //获取连接对象
                Connection conn = dataSourceUtils.createDataSource();
                switch (day) {
                    case "1":
                        //关闭自动提交事务
                        conn.setAutoCommit(false);
                        //同构sql
                        String sql1 = "INSERT INTO env_detail_1 VALUES" +
                                "(?,?,?,?,?,?,?,?,?,?)";
                        //获取预编译对象ps
                        ps1 = conn.prepareStatement(sql1);
                        ps1.setString(1, e.getName());
                        ps1.setString(2, e.getSrcId());
                        ps1.setString(3, e.getDesId());
                        ps1.setString(4, e.getDevId());
                        ps1.setString(5, e.getSensorAddress());
                        ps1.setInt(6, e.getCount());
                        ps1.setString(7, e.getCmd());
                        ps1.setFloat(8, e.getData());
                        ps1.setInt(9, e.getStatus());
                        ps1.setTimestamp(10, e.getGatherDate());
                        ps1.addBatch();
                        break;
                    case "2":
                        //关闭自动提交事务
                        conn.setAutoCommit(false);
                        //同构sql
                        String sql2 = "INSERT INTO env_detail_2 VALUES" +
                                "(?,?,?,?,?,?,?,?,?,?)";
                        //获取预编译对象ps
                        PreparedStatement ps2 = conn.prepareStatement(sql2);
                        ps2.setString(1, e.getName());
                        ps2.setString(2, e.getSrcId());
                        ps2.setString(3, e.getDesId());
                        ps2.setString(4, e.getDevId());
                        ps2.setString(5, e.getSensorAddress());
                        ps2.setInt(6, e.getCount());
                        ps2.setString(7, e.getCmd());
                        ps2.setFloat(8, e.getData());
                        ps2.setInt(9, e.getStatus());
                        ps2.setTimestamp(10, e.getGatherDate());
                        ps2.addBatch();
                        ps1.executeBatch();
                        conn.commit();
                        break;
                    default:
                        conn.close();
                        System.out.println("我先测试两个数据");
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

    }
}
