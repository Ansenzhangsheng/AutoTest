package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

@RestController
@Api(value = "/",description = "这是我全部的post请求")
@RequestMapping("/v1")
public class MyPostMethod {

    private static Cookie cookie;

    //用户登录成功获取到cookie
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口,成功后获取cookie",httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "username",required = true) String username,
                        @RequestParam(value = "password",required = true) String password){
        if(username.equals("Ansen")&&password.equals("123456")){
            cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "恭喜你验证成功了";
        }
        return "用户名或密码错误";
    }

    /**
     *
     */
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                            @RequestBody User u){
        //获取cookie
        Cookie[] cookies = request.getCookies();
        User user = new User();
        user.setName("Ansen1");
        user.setAge("12");
        user.setSex("man");
        //验证cookies是否合法
        for (Cookie c:cookies){
            if(c.getName().equals("login")&&c.getValue().equals("true")
                    &&u.getUsername().equals("Ansen")&&u.getPassword().equals("123456")){

                return user.toString();
            }
        }
        return "参数不合法";
    }
}
