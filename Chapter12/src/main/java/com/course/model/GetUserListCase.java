package com.course.model;

import lombok.Data;

@Data
public class GetUserListCase {

    private String userName;
    private int age;
    private boolean sex;
    private String expected;
}
