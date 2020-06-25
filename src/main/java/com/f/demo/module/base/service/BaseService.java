package com.f.demo.module.base.service;

import com.f.demo.common.constant.ValueConstant;
import com.f.demo.utils.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by matf on 2020-04-18.
 */
@Slf4j
public class BaseService {

//    protected static SnowflakeIdWorker idWorker=new SnowflakeIdWorker(1,1);
    @Autowired
    private IDUtil idUtil;
    protected <T> void initBaseValue(T t ){
        if(t != null){
            try {
                Class clazz = t.getClass();
                Field deleteFlag = clazz.getDeclaredField("deleteFlag");
                if(deleteFlag != null){
                    deleteFlag.setAccessible(true);
                    deleteFlag.set(t, ValueConstant.DELETE_FLAG_NORMAL);
                }
                Date now = new Date();
                Field createTime = clazz.getDeclaredField("createTime");
                if(createTime != null){
                    createTime.setAccessible(true);
                    createTime.set(t, now);
                }
                Field updateTime = clazz.getDeclaredField("updateTime");
                if(updateTime != null){
                    updateTime.setAccessible(true);
                    updateTime.set(t, now);
                }
            } catch (Exception e) {
                log.error("初始化实体类属性",e);
            }
        }
    }

    public String getId() {
        return idUtil.getId();
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            return request;
        }
        return null;
    }

    public HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletResponse response = servletRequestAttributes.getResponse();
            return response;
        }
        return null;
    }

    public  String getIpAddr() {
        HttpServletRequest request = getRequest();
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error("处理ip错误",e);
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

}
