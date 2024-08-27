package com.briup.smart.env.time;

import org.junit.Test;

import java.sql.Timestamp;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-19 17:13
 */
public class TestTimestamp {
    @Test
    public void method(){
        //时间戳：1516323596029
        Time time = new Time();
        //2018-01-19 08:59:56.029
        time.setT(new Timestamp(Long.parseLong("1516323596029")));
        String[] split = time.getT().toString().split("[ ]");
        String[] split1 = split[0].split("-");
        String s = split1[2];
        System.out.println(s);
        System.out.println(split[0]);
        System.out.println(time.getT());
    }
}
class Time{
    private Timestamp t;

    public Time() {
    }

    public Time(Timestamp t) {
        this.t = t;
    }

    /**
     * 获取
     * @return t
     */
    public Timestamp getT() {
        return t;
    }

    /**
     * 设置
     * @param t
     */
    public void setT(Timestamp t) {
        this.t = t;
    }

    public String toString() {
        return "Time{t = " + t + "}";
    }
}
