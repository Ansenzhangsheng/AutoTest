package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/",description = "这是我全部的get方法")
public class MyGetMethod {

    @RequestMapping(value = "/getCookies" ,method = RequestMethod.GET)
    @ApiOperation(value = "通过该方法可以获取cookie值",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        //HttpServletRequest 装请求信息的类
        //HttpServletResponse 装响应信息的类
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "获得cookies成功!";
    }
    /**
     * 要求客户端携带cookie访问
     * 这是一个需要携带cookie信息才能访问的get请求
     */
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "这是一个需要携带cookie信息才能访问的get请求",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            return "你必须携带cookies信息";
        }
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("login") &&
                    cookie.getValue().equals("true")){
                return "访问成功";
            }
        }
        return "你丢失了cookies信息";
    }

    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种实现方式 url:key = value&&key=value
     * 模拟商品获取商品列表
     */
    @RequestMapping(value = "/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value = "带参数第一种实现方式 url:key = value&&key=value",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end){
        Map<String,Integer> myList = new HashMap<>();

        myList.put("鞋",400);
        myList.put("干脆面",1);
        myList.put("衬衫",300);

        return myList;
    }

    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第二种实现方式 url: ip:port/get/with/param/10/20
     * 模拟商品获取商品列表
     */
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "带参数第二种实现方式 url: ip:port/get/with/param/10/20",httpMethod = "GET")
    public Map myGetList(@PathVariable Integer start,
                         @PathVariable Integer end){
        Map<String,Integer> myList = new HashMap<>();

        myList.put("鞋",400);
        myList.put("干脆面",1);
        myList.put("衬衫",300);

        return myList;
    }
}
