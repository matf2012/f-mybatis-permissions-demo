package com.f.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: feng
 * @date: 2020-06-03
 */
@Component
public class ActiveUtil {


    @Value("${spring.profiles.active:dev}")
    private String active;

    String ACTIVE_DEV = "dev";
    String ACTIVE_PRO = "pro";
    String ACTIVE_SIT = "sit";
    String ACTIVE_UAT = "uat";

    public boolean isDev(){
        return ACTIVE_DEV.equals(active) || ACTIVE_SIT.equals(active);
    }
}
