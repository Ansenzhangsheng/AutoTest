package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log
@Log4j
@RestController
@Api(value = "v1",description = "用户管理系统")
@RequestMapping("v1")
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;

    /**
     * 登录接口
     * @param response
     * @param user
     * @return
     */
    @ApiOperation(value = "登录接口",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Boolean login(HttpServletResponse response,@RequestBody User user){
        int i = template.selectOne("login",user);
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        log.info("查询到的结果是"+i);
        if(i==1){
            log.info("登录的用户是"+user.getUserName());
            return true;
        }
        return false;
    }

    /**
     * 添加用户信息
     * @param request
     * @param user
     * @return
     */
    @ApiOperation(value = "添加用户接口",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public boolean addUser(HttpServletRequest request,@RequestBody User user){
        Boolean x = verIfyCookies(request);
        int result = 0;
        if(x!=null){
            result =  template.insert("addUser",user);
        }
        if(result>0){
            log.info("添加用户的数量："+result);
            return true;
        }
        return false;
    }

    /**
     * 获取用户接口
     * @param request
     * @param user
     * @return
     */
    @ApiOperation(value = "获取用户信息列表",httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        log.info("User对象:"+user.toString());
        Boolean x = verIfyCookies(request);
        if(x==true){
            List<User> users = template.selectList("getUserInfo",user);
            log.info("getUserInfo获取到的用户数量是"+users.size());
            return users;
        }else{
            return null;
        }
    }

    /**
     * 更新或删除用户信息接口
     * @return
     */
    @ApiOperation(value = "更新、删除用户接口",httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    public int updateUser(HttpServletRequest request,@RequestBody User user){
        Boolean x = verIfyCookies(request);
        int i = 0;
        if(x==true){
            i = template.update("updateUserInfo",user);
        }
        log.info("更新用户的条目数"+i);
        return i;
    }

    /**
     * 验证cookies接口
     * @param request
     * @return
     */
    private Boolean verIfyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            log.info("cookies 为空");
            return false;
        }
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("login")&&
                    cookie.getValue().equals("true")){
                log.info("cookies验证通过");
                return true;
            }
        }
        return false;
    }


}
