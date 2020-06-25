package com.f.demo.utils;

import org.springframework.stereotype.Component;

/**
 * Created by matf on 2020-05-18.
 */
@Component
public class IDUtil {

    SnowflakeIdWorker idWorker=null;
    public IDUtil(){
        idWorker=new SnowflakeIdWorker(1,1);
    }

    public String getId() {
        return String.valueOf(idWorker.nextId());
    }
}
