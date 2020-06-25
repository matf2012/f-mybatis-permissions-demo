package com.f.demo.common.model.dto;

import lombok.Data;

/**
 * Created by matf on 2020-04-18.
 */
@Data
public class ResultDTO {
    private int code;
    private String message;
    private Object data;
}
