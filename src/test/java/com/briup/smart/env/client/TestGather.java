package com.briup.smart.env.client;

import com.briup.smart.env.entity.Environment;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public class TestGather {
    @Test
    public void method() throws Exception{
        GatherImpl gather = new GatherImpl();
        Collection<Environment> list =
                gather.gather();
        System.out.println(list.size());
    }
    @Test
    public void method2(){
        String s = "GET /?name=jack HTTP/1.1";
        String[] arr = s.split(" ");
        String[] arr2 = arr[1].split("=");

        System.out.println(arr2[1]);
    }
}
