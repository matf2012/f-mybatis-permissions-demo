package com.f.demo.common.configration;

import com.alibaba.fastjson.JSON;
import com.f.demo.common.constant.SecurityConstant;
import com.f.demo.common.model.dto.UserInfoDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义权限匹配和账号密码匹配
 */
public class ShiroRealm extends AuthorizingRealm {


    /**
     * 授权(验证权限时调用)
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证(登录时调用)
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String username = (String) token.getPrincipal();

        UserInfoDTO user = new UserInfoDTO();
        user.setUserName("admin");
        user.setUserPassword("admin");
        if(user != null){

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(JSON.toJSONString(user), user.getUserPassword(), getName());

            return info;
        }
        return null;
    }


}
