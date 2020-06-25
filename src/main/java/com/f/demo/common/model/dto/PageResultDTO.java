package com.f.demo.common.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by matf on 2020-04-18.
 */
@Data
public class PageResultDTO {
    private long total;
    private List list;
}
