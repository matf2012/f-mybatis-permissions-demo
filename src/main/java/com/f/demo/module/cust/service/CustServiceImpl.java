package com.f.demo.module.cust.service;

import com.f.demo.common.model.entity.FCustomer;
import com.f.demo.module.cust.mapper.FCustomerMapper;
import com.f.demo.module.cust.model.dto.CustDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @author: feng
 * @date: 2020-06-25
 */
@Service
@Slf4j
public class CustServiceImpl {

    @Autowired
    private FCustomerMapper customerMapper;

    public List<CustDTO> list(){
        List<FCustomer> list =  customerMapper.listCust();
        return convert(list,CustDTO.class);
    }

    public List<CustDTO> listAll(){
        List<FCustomer> list =  customerMapper.listAllCust();
        return convert(list,CustDTO.class);
    }

    private <O,R> List<R> convert(List<O> list,Class<R> c){
        List<R> ret = new ArrayList<R>();
        if(CollectionUtils.isNotEmpty(list)){
            R dto = null;
            try {
                for(O item : list){
                    dto = c.newInstance();
                    BeanUtils.copyProperties(dto,item);
                }
            } catch (Exception e) {
                log.error("convert error ",e);
            }
        }
        return ret;
    }
}
