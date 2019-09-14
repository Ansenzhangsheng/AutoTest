package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class MyCookiesForPost {

    private String url;
    private ResourceBundle bundle;
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("Application");
        this.url = bundle.getString("test.url");
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

    @Test(dependsOnMethods = "testGetCookies")
    public void testPostMethod() throws IOException {
        String result;
        //测试地址
        String testURL = this.url + bundle.getString("test.post.json.demo");

        //声明一个post方法
        HttpPost post = new HttpPost(testURL);
        //声明一个Client对象
        DefaultHttpClient client = new DefaultHttpClient();
        //添加cookies
        client.setCookieStore(this.store);
        //设置请求头信息
        post.setHeader("Content-Type","Application/json;charset=gbk");
        //添加参数对象
        JSONObject param = new JSONObject();
        param.put("name","Ansen");
        param.put("age","18");
        //将参数添加到请求信息中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //执行post方法
        HttpResponse response = client.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity());
        Reporter.log(result);

        //处理响应结果
        JSONObject resultJson = new JSONObject(result);
        Reporter.log((String) resultJson.get("name"));
        String name = (String) resultJson.get("name");
        int status = response.getStatusLine().getStatusCode();
        Reporter.log(String.valueOf(status));
        Assert.assertEquals(name,"Ansen1");
        Assert.assertEquals(status,200);

    }
}
