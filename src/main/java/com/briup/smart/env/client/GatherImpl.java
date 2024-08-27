package com.briup.smart.env.client;

import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.exception.GatherException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 采集模块的实现类：实现读取数据文件，将数据封装在集合对象中
 * @author lining
 * @Date  2024-7-18
 * @version 1.0
 */
public class GatherImpl implements Gather {
    @Override
    public Collection<Environment> gather() throws Exception {
        //使用字符缓存输入流读取数据文件中每一行
        List<Environment> list = new ArrayList<>();
        String fileName = "D:/Briup/data/data-file-simple";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;//每行数据

/*        FileInputStream fis = new FileInputStream(fileName);
        int size=fis.available();*/
        while((line = br.readLine()) != null){
            //根据分隔符拆分数据
            String[] arr = line.split("[|]");
            //将数组元素添加到环境对象中，环境对象的属性值
            Environment env = new Environment();
            init(env,arr);
            //根据传感器编号判断，对name和data进行赋值
            switch (arr[3]) {
                case "16":
                    env.setName("温度");
                    //57a4 ---10进制 --》公式计算
                    env.setData((Integer.parseInt(arr[6].substring(0,4),16) * (0.00268127F))-46.85F);
                    //当传感器为温湿度时，需要新增一个环境对象表示湿度
                    Environment humidityEnv = new Environment();
                    init(humidityEnv,arr);
                    humidityEnv.setData((Integer.parseInt(arr[6].substring(4,8),16) *0.00190735F)-6);
                    humidityEnv.setName("湿度");
                    list.add(humidityEnv);
                    break;
                case "256":
                    env.setName("光照强度");
                    //arr[6]---substr---String(16进制)---int10进制--->float
                    env.setData(Integer.parseInt(arr[6].substring(0,4),16));
                    break;
                case "1280":
                    env.setName("二氧化碳");
                    env.setData(Integer.parseInt(arr[6].substring(0,4),16));
                    break;
                default:
                    throw new GatherException("数据存在问题");
            }
            list.add(env);
        }
/*        BackupImpl backup = new BackupImpl();
        backup.store("D:/back-up",size,Backup.STORE_OVERRIDE);*/
        return list;
    }

    /**
     * 实现环境对象初始化操作，除了name和data外
     * @param env
     * @param arr
     */
    private void init(Environment env,String[] arr){
        env.setSrcId(arr[0]);
        env.setDesId(arr[1]);
        env.setDevId(arr[2]);
        env.setSensorAddress(arr[3]);
        env.setCount(Integer.parseInt(arr[4]));
        env.setCmd(arr[5]);
        env.setStatus(Integer.parseInt(arr[7]));
        //如何将String---long---TimeStamp
        env.setGatherDate(new Timestamp(Long.parseLong(arr[8])));
    }
}
