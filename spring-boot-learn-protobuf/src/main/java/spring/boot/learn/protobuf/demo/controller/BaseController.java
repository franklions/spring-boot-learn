package spring.boot.learn.protobuf.demo.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/16
 * @since Jdk 1.8
 */
@ControllerAdvice
public class BaseController {
    //请求类型不对
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> requestHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return fail(415, "Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }

    //请求内容不能解析
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> requestHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        return fail(400, "Bad Request", HttpStatus.BAD_REQUEST);
    }

    //请求内容不能解析
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> requestHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        return fail(405, "Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }

    //参数类型不匹配
    @ExceptionHandler({TypeMismatchException.class})
    public ResponseEntity<?> requestTypeMismatch(TypeMismatchException e) {

        return fail(415, "Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> requestMethodArgumentNotValid(MethodArgumentNotValidException e) {

        return fail(400, "Bad Request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<?> missingServletRequestParameterException(MethodArgumentNotValidException e) {

        return fail(400, "Bad Request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleAllError(HttpServletRequest request, Throwable ex) {

        return fail(500, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> fail(double code, String message, HttpStatus status) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(status);
        return builder.body(code+message);
    }
}
