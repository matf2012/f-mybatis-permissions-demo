package com.f.demo.common.configration;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 跨域认证处理类
 */
public class CORSAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CORSAuthenticationFilter.class);
    /**
     * 直接过滤可以访问的请求类型
     */
    private static final String REQUET_TYPE = "OPTIONS";



    public CORSAuthenticationFilter() {
        super();
    }
    public CORSAuthenticationFilter(String checkUrl) {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //Always return true if the request's method is OPTIONS
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals(REQUET_TYPE)) {
            return true;
        }
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        if (uri != null && uri.startsWith("/file")) {
            return true;
        }
        if (uri != null && uri.indexOf("/wechat/check")>=0) {
            return true;
        }else if (uri != null && uri.indexOf("/wechat/orderNotify")>=0) {
            return true;
        }

        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 50014);
        map.put("msg", "未登录");
        writer.write(JSON.toJSONString(map));
        writer.close();
        return false;
    }

}
