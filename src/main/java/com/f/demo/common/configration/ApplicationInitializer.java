package com.f.demo.common.configration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by matf on 2020-04-18.
 */
@Component
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {


    private static ApplicationContext context;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.context = contextRefreshedEvent.getApplicationContext();

    }

    public static <T> T getBean(Class<T> c) {
        if (context != null) {
            return context.getBean(c);
        }
        return null;
    }


}
