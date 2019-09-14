package com.cource.testng;

import org.testng.annotations.Test;

public class DependTest {

    @Test(enabled = true)
    public void test1(){
        System.out.println("test1");
    }
    @Test(dependsOnMethods = {"test1"})
    public void test2(){
        System.out.println("test2执行");
    }
    @Test
    public void test3(){
        System.out.println("test3");
    }
}
