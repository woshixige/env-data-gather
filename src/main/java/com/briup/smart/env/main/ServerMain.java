package com.briup.smart.env.main;

import com.briup.smart.env.server.Server;
import com.briup.smart.env.server.ServerImpl;

/**
 * 接收服务器程序入口
 */
public class ServerMain {

    public static void main(String[] args)throws Exception {
        //main线程
        Server server = new ServerImpl();
        //main-->新增一个Thread--->开启关闭服务器
        server.shutdown();
        //main--》开启接收服务器
        server.receive();
    }
}
