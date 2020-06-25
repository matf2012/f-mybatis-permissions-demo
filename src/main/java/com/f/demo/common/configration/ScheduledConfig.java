package com.f.demo.common.configration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 定时任务配置类
 * Created by matf on 2019-10-29.
 */
public class ScheduledConfig implements SchedulingConfigurer {

    @Value("${job.threadSize}")
    private Integer corePoolSize = 5;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(setTaskExecutors());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor setTaskExecutors() {
        return Executors.newScheduledThreadPool(corePoolSize);
    }
}
