package com.baizhi.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;

    private String name;

    private String picImg;

    private String password;

    private String legalName;

    private String sex;

    private String salt;

    private String phone;

    private String status;

    private String city;

    private String sign;

    private String regTime;

    private String guruId;
}