package com.cource.testng.paramter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    @Test(dataProvider = "data")
    public void testDataprovider(String name,int age){
        System.out.println("name:"+name+"\nage:"+age);
    }
    @DataProvider(name="data")
    public Object[][] providerData(){
        Object[][] o = new Object[][]{
                {"zhangsan",10},
                {"lisi",9}
        };
        return o;
    }

    @Test(dataProvider = "methodData")
    public void test1(String name,int age){
        System.out.println("test1方法 name:"+name+";age:"+age);
    }
    @Test(dataProvider = "methodData")
    public void test2(String name,int age){
        System.out.println("test2方法 name:"+name+";age:"+age);
    }

    @DataProvider(name = "methodData")
    public Object[][] methodDataTest(Method method){
        Object[][] result = null;
        if(method.getName().equals("test1")){
            result = new Object[][]{
                    {"zhangsheng1",1},
                    {"zhangsheng2",2}
            };
        }else if (method.getName().equals("test2")){
            result = new Object[][]{
                    {"Ansen1",1},
                    {"Ansen2",2}
            };
        }
        return result;
    }
}
