package spring.boot.learn.aop.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/16
 * @since Jdk 1.8
 */
@Component
@Aspect
public class AdviceDemo {

    @Around("execution(* spring.boot.learn.aop.demo.service.*.many*(..) )")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("@Around:执行目标方法之前...");
        //访问目标方法参数
        Object[] args = joinPoint.getArgs();
        if(args != null && args.length >0 && args[0].getClass() == String.class){
            args[0] = "改变后的参数1";
        }
        //用改变后的参数执行目标方法
        Object returnValue = joinPoint.proceed(args);
        System.out.println("@Around:执行目标方法之后...");
        System.out.println("@Around:被织入的目标对象为："+ joinPoint.getTarget());
        return "原返回值："+returnValue + "，这是返回结果的后缀";
    }

    @Before("execution(* spring.boot.learn.aop.demo.service.*.many*(..) )")
    public void permissionCheck(JoinPoint point){
        System.out.println("@Before:模拟权限检查...");
        System.out.println("@Before:目标方法为：" +
            point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        System.out.println("@Before:参数为："+ Arrays.toString(point.getArgs()));
        System.out.println("@Before:被织入的目标对象为："+point.getTarget());
    }

    @AfterReturning(pointcut = "execution(* spring.boot.learn.aop.demo.service.*.many*(..))",
            returning = "returnValue")
    public void log(JoinPoint point,Object returnValue){
        System.out.println("@AfterReturning模拟日志记录功能....");
        System.out.println("@AfterReturning:目标方法为："+
                point.getSignature().getDeclaringTypeName() +
                "."+point.getSignature().getName());
        System.out.println("@AfterReturning:参数为："+Arrays.toString(point.getArgs()));
        System.out.println("@AfterReturning:返回值为："+returnValue);
        System.out.println("@AfterReturning:被织入的目标对象为："+point.getTarget());
    }

    @After("execution(* spring.boot.learn.aop.demo.service.*.many*(..) )")
    public void releaseResource(JoinPoint point){
        System.out.println("@After:模拟释放资源...");
        System.out.println("@After:目标方法为："+
            point.getSignature().getDeclaringTypeName() +
            "."+point.getSignature().getName());
        System.out.println("@After:参数为："+Arrays.toString(point.getArgs()));
        System.out.printf("@After:被织入的目标对象为："+ point.getTarget());
    }

}
