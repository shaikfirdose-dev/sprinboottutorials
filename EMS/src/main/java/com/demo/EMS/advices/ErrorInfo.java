package com.demo.EMS.advices;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorInfo {

    private String message;
    private String httpStatus;
    private String httpCode;
    private List<String> errors;
}
