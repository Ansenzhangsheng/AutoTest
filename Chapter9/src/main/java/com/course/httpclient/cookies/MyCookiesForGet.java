package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

    private String url;
    private ResourceBundle bundle;
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        //从配置文件中获取测试链接
        String testURL = this.url+bundle.getString("getCookies.uri");
        HttpGet get = new HttpGet(testURL);

        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

        //获取cookies信息
        this.store =  client.getCookieStore();
        List<Cookie> cookieLise =  store.getCookies();

        for (Cookie cookie:cookieLise){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name="+name+";value="+value);
        }

    }

    /**
     * 从testGetCookies中获取cookies
     * 并利用cookies访问/test/get/whit/cookies
     * */
    @Test(dependsOnMethods = "testGetCookies")
    public void testGetWhitCookies() throws IOException {
        String result;
        String testURL = this.url+bundle.getString("test.get.with.cookies");
        HttpGet get = new HttpGet(testURL);

        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(this.store);
        HttpResponse response =  client.execute(get);

        //获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode:"+statusCode);

        if(statusCode == 200){
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }else {
            System.out.println("失败");
        }

    }

}
