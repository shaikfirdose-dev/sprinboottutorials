//package com.firdose.week4RestClient.week4Tutorials.advices;
//
//import com.firdose.springbootwebweek2.springbootwebweek2.exceptions.ResourceNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler{
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<APIResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception){
//        APIError apiError = APIError
//                .builder()
//                .httpStatus(HttpStatus.NOT_FOUND)
//                .message(exception.getMessage())
//                .build();
//        return buildAPIResponseConverter(apiError);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<APIResponse<?>> handleMethodArgumentNotValidFoundException(MethodArgumentNotValidException exception){
//        List<String> allErrors = exception
//                .getBindingResult()
//                .getAllErrors()
//                .stream()
//                .map(error -> error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        APIError apiError = APIError
//                .builder()
//                .httpStatus(HttpStatus.BAD_REQUEST)
//                .message("Input validation failed")
//                .errors(allErrors).build();
//        return buildAPIResponseConverter(apiError);
//    }
//
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<APIResponse<?>> handleInternalServerException(Exception exception){
//        APIError apiError = APIError.builder()
//                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                .message(exception.getMessage())
//                .build();
//        return buildAPIResponseConverter(apiError);
//    }
//
//    private ResponseEntity<APIResponse<?>> buildAPIResponseConverter(APIError apiError) {
//        return new ResponseEntity<>(new APIResponse<>(apiError), apiError.getHttpStatus());
//    }
//
//
//}