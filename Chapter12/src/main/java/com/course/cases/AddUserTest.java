package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口")
    public void addUser() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase",1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //发送请求获取结果
        String result = getRrsult(addUserCase);
        //验证返回结果
        Assert.assertEquals(addUserCase.getExpected(),result);
    }

    private String getRrsult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject params = new JSONObject();
        params.put("userName",addUserCase.getUserName());
        params.put("age",addUserCase.getAge());
        params.put("password",addUserCase.getPassword());
        params.put("isPermission",addUserCase.isPermission());
        params.put("isDelete",addUserCase.isDelete());
        params.put("sex",addUserCase.isSex());

        //设置头信息
        post.setHeader("content-type","application/json");

        StringEntity entity = new StringEntity(params.toString(), "utf-8");
        post.setEntity(entity);

        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        String result; //存放返回结果
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        return result;
    }

}
