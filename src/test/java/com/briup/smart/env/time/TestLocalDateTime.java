package com.briup.smart.env.time;

import com.briup.smart.env.client.GatherImpl;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.server.DBStoreImpl;
import com.briup.smart.env.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-22 15:15
 */
public class TestLocalDateTime {
    @Test
    public void method(){
        long millis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(millis);
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        System.out.println(year+"-"+month+"-"+day);
        //日期转时间戳
        LocalDateTime ldt = LocalDateTime.of(2024, 10, 8, 10, 0, 0);
        long l = ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(l);
    }
    @Test
    public void method2() throws Exception {
        GatherImpl gather = new GatherImpl();
        Collection<Environment> coll = gather.gather();
        System.out.println(coll.size());
        DBStoreImpl dbStore = new DBStoreImpl();
        dbStore.saveDB(coll);
    }
    @Test
    public void method3() throws Exception {
        long millis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(millis);
        Connection conn = JDBCUtil.getConnection();
        int day=1;
        conn.setAutoCommit(false);
        String sql = "INSERT INTO env_detail_" + day + " VALUES" +
                "(?,?,?,?,?,?,?,?,?,?)";//同一天的放在同一张表
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "111");
        ps.setString(2, "111");
        ps.setString(3, "111");
        ps.setString(4, "111");
        ps.setString(5, "111");
        ps.setInt(6, 1);
        ps.setString(7, "111");
        ps.setFloat(8, 1.1f);
        ps.setInt(9, 2);
        ps.setTimestamp(10, timestamp);
        ps.execute();
        conn.commit();
        conn.close();
    }
    @Test
    public void method4(){
        long millis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(millis);
        System.out.println(timestamp.toLocalDateTime());
        int day = timestamp.toLocalDateTime().getDayOfMonth();
        System.out.println(day);
    }
}
