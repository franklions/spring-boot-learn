package com.franklions.springMVC.annotation;

import java.lang.annotation.*;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-03-30
 * @since Jdk 1.8
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FLSController {
    String value() default "";
}
