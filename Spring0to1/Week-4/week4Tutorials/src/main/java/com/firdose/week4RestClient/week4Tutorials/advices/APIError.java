package com.firdose.week4RestClient.week4Tutorials.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class APIError {

   private HttpStatus httpStatus;
   private String message;
   private List<String> errors;
}
