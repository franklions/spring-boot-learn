package spring.boot.learn.aop.demo.aspect;

import java.lang.annotation.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/25
 * @since Jdk 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface ParamaterIgnore {
}
