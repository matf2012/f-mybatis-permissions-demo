package com.f.demo.common.configration;

import com.f.demo.common.constant.SecurityConstant;
import com.f.demo.common.model.dto.UserInfoDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * Created by matf on 2020-04-19.
 */
public class SessionUtil {


    /**
     * 获取SESSION
     * @return
     */
    public static Session getSession(){

        Session session = SecurityUtils.getSubject().getSession();
        return session;
    }



}
