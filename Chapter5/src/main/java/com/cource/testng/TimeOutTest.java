package com.cource.testng;

import org.testng.annotations.Test;
/**
 * TestNG超时测试
 * */
public class TimeOutTest {

    @Test(timeOut = 3000)
    public void testSuccess() throws InterruptedException {
        Thread.sleep(2000);
    }
}
