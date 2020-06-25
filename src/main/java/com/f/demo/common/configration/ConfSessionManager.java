package com.f.demo.common.configration;

import com.f.demo.common.constant.SecurityConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义sessionId获取类
 */
public class ConfSessionManager extends DefaultWebSessionManager {

    public ConfSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //获取请求头，或者请求参数中的Token
        String id = WebUtils.toHttp(request).getHeader(SecurityConstant.AUTH_TOKEN);
        // 如果请求头中有 Authorization 则其值为sessionId
        if (StringUtils.isNotEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, SecurityConstant.REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

            return id;
        } else {
            // 否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }

}
