package com.api.dataHub.controller.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseSimpleVo {
    private Integer code;
    private String message;
}
