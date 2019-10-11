package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取httpClient对象")
    public void beforeTest(){
        TestConfig.getUserInforUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);

        TestConfig.defaultHttpClient = new DefaultHttpClient();

    }

    //目前测试用例中只是获取了存在数据库中的请求接口的请求参数及接口url
    @Test(groups = "loginTrue",description = "用户登陆成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //发送请求
        String result = getResult(loginCase);
        //验证结果
        Assert.assertEquals(loginCase.getExpected(),result);

    }

    @Test(groups = "loginFalse",description = "用户登陆失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //发送请求
        String result = getResult(loginCase);
        //验证结果
        Assert.assertEquals(loginCase.getExpected(),result);

    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject params = new JSONObject();
        params.put("userName","Ansen1");
        params.put("password","123456");
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(params.toString());
        post.setEntity(entity);
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();

        return result;
    }
}
