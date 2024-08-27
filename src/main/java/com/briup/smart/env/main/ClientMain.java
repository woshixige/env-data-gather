package com.briup.smart.env.main;

import com.briup.smart.env.client.ClientImpl;
import com.briup.smart.env.client.GatherImpl;
import com.briup.smart.env.entity.Environment;

import java.util.Collection;

/**
 * 客户端程序入口
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        GatherImpl gather = new GatherImpl();
        Collection<Environment> coll = gather.gather();

        ClientImpl client = new ClientImpl();
        client.send(coll);
    }
}
