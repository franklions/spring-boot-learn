package spring.boot.learn.filter.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/22
 * @since Jdk 1.8
 */
public class BaseController {
    Logger logger = LogManager.getLogger(BaseController.class);

    //请求类型不对
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> requestHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        logger.warn(e.getMessage());
        return this.fail(415, "Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    private ResponseEntity<?> fail(int i, String s, HttpStatus unsupportedMediaType) {
        return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //请求内容不能解析
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> requestHttpMessageNotReadableException(HttpMessageNotReadableException e){
        logger.warn(e.getMessage());
        return this.fail(400, "Bad request", HttpStatus.BAD_REQUEST);
    }

    //请求内容不能解析
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> requestHttpRequestMethodNotSupportedException (HttpRequestMethodNotSupportedException   e){
        logger.warn(e.getMessage());
        return this.fail(405, "Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }

    //参数类型不匹配
    @ExceptionHandler({TypeMismatchException.class})
    public ResponseEntity<?> requestTypeMismatch(TypeMismatchException e){
        logger.warn(e.getMessage());
        return this.fail(400, "Bad request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> requestMethodArgumentNotValid(MethodArgumentNotValidException e){
        logger.warn(e.getMessage());
        return this.fail(400, "Bad request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleAllError(HttpServletRequest request, Throwable ex) {
        logger.error(ex.toString());
        return this.fail(500, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
