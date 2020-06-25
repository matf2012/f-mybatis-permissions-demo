package com.f.demo.common.configration;


import com.f.permissions.mybatis.IValuesHandler;
import com.f.permissions.mybatis.PermissionsConstant;
import com.f.permissions.mybatis.WhereDTO;
import com.neusoft.permissions.constants.PermissionsOperator;
import com.neusoft.permissions.entity.PermissionsWhereDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: feng
 * @date: 2020-06-25
 */
@Component
public class FValueHandler implements IValuesHandler {

    int val = 1;

    @Override
    public List<PermissionsWhereDTO> values(String[] permissionsValue) {
        List<PermissionsWhereDTO> list = new ArrayList<>();

        List<String> orgValues = new ArrayList<>();
        orgValues.add("1");
        if((val++)%2==0){
            orgValues.add("2");
        }

        for(String p :  permissionsValue){
            WhereDTO where = convertPermissionValue(p);
            if(PermissionsConstant.ORG_LIST.equals(where.getType()) && orgValues.size() > 0){
                where.setValues(orgValues);
                if(orgValues.size() == 1){
                    where.setOperator(PermissionsOperator.STARTWITH);
                }else{
                    where.setOperator(PermissionsOperator.IN);
                }
                list.add(where);
            }
        }
        return list;
    }

    @Override
    public String permissionsValue() {
        return "test permissions value";
    }

    protected WhereDTO convertPermissionValue(String permission){
        WhereDTO dto = new WhereDTO();
        if(permission != null){
            dto.setType(PermissionsConstant.ORG_LIST);
            String[] pArray = permission.split(":");
            if(pArray.length > 1){
                dto.setColumn(pArray[1]);
            }
            if(pArray.length > 0){
                dto.setTableName(pArray[0]);
            }
            if(pArray.length > 2){
                // 只有一个 只取默认值 机构 PermissionsType.ORG_CODE
            }
        }
        return dto;
    }
}
