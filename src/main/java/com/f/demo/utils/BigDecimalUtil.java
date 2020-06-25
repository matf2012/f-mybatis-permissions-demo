package com.f.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * Created by matf on 2020-04-18.
 */
@Slf4j
public class BigDecimalUtil {

    public static BigDecimal getVal(String str){
        BigDecimal ret = null;
        if(StringUtils.isNotBlank(str)){
            try {
                ret = new BigDecimal(str);
            } catch (Exception e) {
                log.error("字符转BigDecimal错误:"+str,e);
            }
        }
        return ret;
    }
}
