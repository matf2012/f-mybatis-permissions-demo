package com.f.demo.module.cust.controller;

import com.f.demo.module.cust.model.dto.CustDTO;
import com.f.demo.module.cust.service.CustServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by matf on 2020-05-16.
 */
@RestController
@RequestMapping("/cust/")
public class CustController {

    @Autowired
    private CustServiceImpl custService;


    @RequestMapping(value = "list" , method = {RequestMethod.POST})
    public List<CustDTO> list(){
        return custService.list();
    }

    @RequestMapping(value = "listAll" , method = {RequestMethod.POST})
    public List<CustDTO> listAll(){
        return custService.listAll();
    }


}
