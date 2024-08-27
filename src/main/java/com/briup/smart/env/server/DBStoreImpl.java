package com.briup.smart.env.server;

import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-22 14:04
 */
public class DBStoreImpl implements DBStore {
    @Override
    public void saveDB(Collection<Environment> collection) throws Exception {
        //获取连接对象
        Connection conn = JDBCUtil.getConnection();
        //关闭自动提交
        conn.setAutoCommit(false);
        PreparedStatement ps = null;
        int day = 0;//采集数据的天数
        int date = 0;//上一次的日期数据
        int count = 0;
        for (Environment env : collection) {
            //获取时间戳
            day = env.getGatherDate().toLocalDateTime().getDayOfMonth();
            if (day != date) {
                //当执行到第二天的采集数据时，要执行上一天的批处理
                if (ps != null) {
                    ps.executeBatch();
                    conn.commit();
                }
                String sql = "INSERT INTO env_detail_" + day + " VALUES" +
                        "(?,?,?,?,?,?,?,?,?,?)";//同一天的放在同一张表
                //获取预编译对象ps
                ps = conn.prepareStatement(sql);
                date = day;//重新赋值
            }
            ps.setString(1, env.getName());
            ps.setString(2, env.getSrcId());
            ps.setString(3, env.getDesId());
            ps.setString(4, env.getDevId());
            ps.setString(5, env.getSensorAddress());
            ps.setInt(6, env.getCount());
            ps.setString(7, env.getCmd());
            ps.setFloat(8, env.getData());
            ps.setInt(9, env.getStatus());
            ps.setTimestamp(10, env.getGatherDate());
            //将数据添加到批处理中
            ps.addBatch();//同一天的一批
            count++;//统计添加到批处理的条数
            if (count % 500 == 0) {//超过500条统一提交一次
                ps.executeBatch();
            }
        }

        ps.executeBatch();//只有一天的数据，最后一天不足500条的数据时
        //手动提交事务
        conn.commit();
        JDBCUtil.close(ps, conn);
    }
}
