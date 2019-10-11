package com.course.model;

import lombok.Data;

@Data
public class AddUserCase {

    private String userName;
    private String password;
    private boolean sex;
    private int age;
    private boolean permission;
    private boolean isDelete;
    private String expected;
}
