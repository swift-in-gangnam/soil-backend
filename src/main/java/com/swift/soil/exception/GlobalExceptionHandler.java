package com.swift.soil.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.swift.soil.exception.ExceptionCode.DUPLICATE_RESOURCE;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ExceptionResponse> handleDataException() {
        return ExceptionResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        return ExceptionResponse.toResponseEntity(e.getExceptionCode());
    }
}
//@RestControllerAdvice   // 전역 설정을 위한 annotaion
//public class GlobalExceptionHandler {
//    //MethodArgumentNotValidException 처리를 위한 메소드
////    @ExceptionHandler(MethodArgumentNotValidException.class)
////    public BaseResponse<String> processValidationError(MethodArgumentNotValidException exception) {
////        BindingResult bindingResult = exception.getBindingResult();
////
////        StringBuilder builder = new StringBuilder();
////        for (FieldError fieldError : bindingResult.getFieldErrors()) {
////            builder.append(fieldError.getDefaultMessage());
////        }
////
////        return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST);
////    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<BaseResponse> processValidationError(MethodArgumentNotValidException exception) {
//        BindingResult bindingResult = exception.getBindingResult();
//
//        StringBuilder builder = new StringBuilder();
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            builder.append(fieldError.getDefaultMessage());
//        }
//
//        return new ResponseEntity<>(new BaseResponse(BaseResponseStatus.VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
//    }
//
//    //hibernate
//    @ExceptionHandler(value = {ConstraintViolationException.class,
//            DataIntegrityViolationException.class})
//    protected ResponseEntity<BaseResponse> handleDataException() {
//        return new ResponseEntity<>(new BaseResponse(BaseResponseStatus.DUPLICATE_ERROR), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(value = {IllegalArgumentException.class})
//    protected ResponseEntity<BaseResponse> TokenNotInHeaderException() {
//        return new ResponseEntity<>(new BaseResponse(BaseResponseStatus.TOKEN_NOT_IN_HEADER), HttpStatus.BAD_REQUEST);
//    }
//
//    //hibernate
//    @ExceptionHandler(value = {CustomException.class})
//    protected ResponseEntity<BaseResponse> handleBaseException() {
//        return new ResponseEntity<>(new BaseResponse(BaseResponseStatus.TOKEN_EXPIRED), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(value = {NoHandlerFoundException.class})
//    protected ResponseEntity<BaseResponse> handleNoHandlerFoundException() {
//        return new ResponseEntity<>(new BaseResponse(BaseResponseStatus.URL_NOT_FOUND), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
//    protected ResponseEntity<BaseResponse> MethodArgumentTypeMismatchException() {
//        return new ResponseEntity<>(new BaseResponse(BaseResponseStatus.METHOD_TYPE_MISMATCH), HttpStatus.BAD_REQUEST);
//    }
//
//    //Global Exception
////    @ExceptionHandler(value = {Exception.class})
////    protected ResponseEntity<BaseResponse> handleGlobalException(Exception exception) {
////        return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST, exception.getMessage());
////    }
//
//    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
//    protected ResponseEntity<BaseResponse> handleHttpRequestMethodNotSupportedException() {
//        return new ResponseEntity<>(new BaseResponse(BaseResponseStatus.METHOD_NOT_SUPPORTED), HttpStatus.BAD_REQUEST);
//    }
//
//    // 404 Exception
//}