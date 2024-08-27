package com.briup.smart.env.client;

import com.briup.smart.env.entity.Environment;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;

/**
 * 网络模块客户端实现类
 */
public class ClientImpl implements Client {
    @Override
    public void send(Collection<Environment> collection) throws Exception {
        //1.创建客户端对象
        Socket socket = new Socket("127.0.0.1", 9999);
        System.out.println("启动客户端1");
        //2.获取IO输出流  发送数据
        OutputStream os = socket.getOutputStream();
        //3.包装流写数据
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(collection);
        System.out.println("客户端1发送成功");
        //4.刷新流
        oos.flush();
        //5.关闭资源
        oos.close();
        socket.close();
    }
}
