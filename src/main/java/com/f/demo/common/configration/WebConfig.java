package com.f.demo.common.configration;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by matf on 2020-04-18.
 */

@Configuration
@EnableAsync
public class WebConfig {


    @Value("${spring.redis.host:127.0.0.1}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password:}")
    private String password;


    @Value("${spring.redis.expire:}")
    private int expire;

    @Value("${spring.redis.timeout:3000}")
    private int timeout;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(10);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("hello-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        /**==============================版本1=============================**/
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置遇到未登录、未授权等情况时候，请求这些地址，返回相应的错误
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");

        //拦截器，配置访问权限 必须是LinkedHashMap，因为它必须保证有序。滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/user/**", "anon");
        filterChainDefinitionMap.put("/cust/**", "anon");
        filterChainDefinitionMap.put("/product/**", "anon");
        filterChainDefinitionMap.put("/file/**", "anon");
        filterChainDefinitionMap.put("/wechat/**", "anon");

        //swagger ui
        filterChainDefinitionMap.put("/configuration/ui","anon");
        filterChainDefinitionMap.put("/swagger-resources","anon");
        filterChainDefinitionMap.put("/configuration/security","anon");
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/webjars/**","anon");
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/swagge‌​r-ui.html","anon");
        filterChainDefinitionMap.put("/actuator/**","anon");
        filterChainDefinitionMap.put("/druid/**","anon");
        filterChainDefinitionMap.put("/v2/api-docs","anon");

        //authc:所有url必须通过认证才能访问，anon:所有url都可以匿名访问
        filterChainDefinitionMap.put("/**", "corsAuthenticationFilter");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //自定义过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        // TODO
        filterMap.put("corsAuthenticationFilter", corsAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        /**==============================版本1=============================**/
        return shiroFilterFactoryBean;
    }


    public CORSAuthenticationFilter corsAuthenticationFilter() {
        return new CORSAuthenticationFilter();
    }


    /**
     * 身份认证realm
     *
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm myShiroRealm = new ShiroRealm();
        return myShiroRealm;
    }

    /**
     * 自定义 securityManager
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        // 自定义session管理 使用redis
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());

        return securityManager;
    }

    /**
     * 自定义sessionManager
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        ConfSessionManager sessionManager = new ConfSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
//        sessionManager.setGlobalSessionTimeout(7 * 24 *60 * 60 * 1000);
        return sessionManager;
    }

    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host+":"+port);
        redisManager.setTimeout(timeout);
        if(StringUtils.isNotBlank(password)){
            redisManager.setPassword(password);
        }
        return redisManager;
    }
}
