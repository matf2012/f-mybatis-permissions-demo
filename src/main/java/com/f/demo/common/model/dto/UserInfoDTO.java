package com.f.demo.common.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by matf on 2020-05-17.
 */
@Data
public class UserInfoDTO implements Serializable {

    private String id;

    private String userTel;

    private String userName;

    private String userPassword;

    private String openid;

}
