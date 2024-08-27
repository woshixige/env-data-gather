package com.briup.smart.env.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-07-19 9:17
 */
public class TestJunit {
    @Before
    public void before(){
        System.out.println("before...");
    }
    @Test
    public void method(){
        System.out.println("test...");
    }
    @After
    public void after(){
        System.out.println("after...");
    }
}
