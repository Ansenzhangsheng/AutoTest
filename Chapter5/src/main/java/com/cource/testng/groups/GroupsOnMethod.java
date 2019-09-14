package com.cource.testng.groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupsOnMethod {
    @Test(groups = "server")
    public void test1(){
        System.out.println("服务端测试1");
    }
    @Test(groups = "server")
    public void test2(){
        System.out.println("服务端测试2");
    }
    @Test(groups = "client")
    public void test3(){
        System.out.println("客户端测试3");
    }

    @BeforeGroups("server")
    public void beforeGroupsOnServer(){
        System.out.println("这是服务端组运行方式之前运行的方法");
    }
    @AfterGroups("server")
    public void afterGroupsOnServer(){
        System.out.println("这是服务端组运行方法之后运行的方法!!!!");
    }

}
