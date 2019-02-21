package com.contentsale.service.model;

import javax.validation.constraints.NotBlank;

/**
 * Created by wss on 2019/1/9.
 */
public class UserModel {


    private Integer id;


   //@NotBlank(message = "用户名不能为空")
    private String name;

    private Byte type;

    private String salt;

    //@NotBlank(message = "密码不能为空")
    private String encryptPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

}

