package com.briup.smart.env.client;

import java.io.*;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-23 14:06
 */
public class BackupImpl implements Backup {
    @Override
    public Object load(String filePath, boolean del) throws Exception {
        File file = new File(filePath);
        if (!file.exists()){
            return null;
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        if (del){
            file.delete();
        }
        return ois.readObject();
    }

    @Override
    public void store(String filePath, Object obj, boolean append) throws Exception {
        //1.对象流吸入数据到指定文件
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath,append));
        //2.写出数据
        oos.writeObject(obj);
        //3.刷新流
        oos.flush();
        //4.关闭流
        if (oos!=null) oos.close();

    }
}
