package com.f.demo.common.model.dto;

import lombok.Data;

/**
 * Created by matf on 2020-04-18.
 */
@Data
public abstract class PageParamDTO {
    private int pageNo;
    private int pageSize;
}
