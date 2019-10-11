package com.course.model;

import lombok.Data;

@Data
public class User {

    private  int id;
    private String userName;
    private int age;
    private boolean sex;
    private boolean permission;
    private String isDelete;
    private String password;

    public String toString(){
        return (
            "{id:"+id+","+
                    "userName:"+userName+","+
                    "sex:"+sex+","+
                    "permission:"+permission+","+
                    "isDelete:"+isDelete+","+
                    "password:"+password+"}"
        );
    }
}
