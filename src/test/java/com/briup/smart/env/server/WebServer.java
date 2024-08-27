package com.briup.smart.env.server;

import com.briup.smart.env.client.ClientImpl;
import com.briup.smart.env.client.GatherImpl;
import com.briup.smart.env.entity.Environment;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

/**
 * 编写一个web服务器，接收浏览器请求并作出相应信息
 */
public class WebServer {
    public static void main(String[] args) throws Exception {
        //1.创建服务器
        ServerSocket server = new ServerSocket(8888);
        System.out.println("开启web服务器");
        //2.等待客户端连接
        System.out.println("等待浏览器访问");
        Socket socket = server.accept();//套接字（网络传输细节封装起来）
        System.out.println("连接成功");
        //3.接收浏览器发送过啦的请求报文（字符串信息）
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        //4.使用包装流读请求信息
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        //5.获取请求行数据
        String requestLine = br.readLine();
        //GET /?name=jack HTTP/1.1
        System.out.println(requestLine);
        //6.解析请求行数据中名字
        String[] arr = requestLine.split(" ");
        String[] arr2 = arr[1].split("=");
        String name = arr2[1];//tom

        //使用包装流将数据写出到浏览器中
        PrintWriter pw = new PrintWriter(out);
        //写出一个响应报文：响应行 响应头 空格 响应体
        pw.println("HTTP/1.1 200 OK");//响应行
        pw.println("Content-Type: text/html;charset=utf-8");//响应头
        pw.println();//空格
        pw.println("<h1>你好 "+name+"</h1>");
        //刷新流
        pw.flush();
        pw.close();
        //关闭资源
        server.close();
        // http://127.0.0.1/?name=jack


    }
    @Test
    public void method02() throws Exception {
        GatherImpl gather = new GatherImpl();
        Collection<Environment> coll = gather.gather();
        System.out.println(coll);
        /*ClientImpl client = new ClientImpl();
        client.send(coll);*/
    }
    @Test
    public void method() throws Exception {
        ServerImpl server = new ServerImpl();
        server.receive();
        server.shutdown();
    }
}
