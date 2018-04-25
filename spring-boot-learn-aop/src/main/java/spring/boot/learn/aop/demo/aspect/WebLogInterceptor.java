package spring.boot.learn.aop.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/16
 * @since Jdk 1.8
 */
@Component
@Aspect
public class WebLogInterceptor {

    @Pointcut("@annotation(spring.boot.learn.aop.demo.aspect.LogEnable) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void pointcutGet(){}

    @Around("pointcutGet()")
    public Object round(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("@Around:执行目标方法之前...");
        String methodRemark = getMthodRemark(joinPoint);
        Object result = null;
        try {
            // 记录操作日志...谁..在什么时间..做了什么事情..
            result = joinPoint.proceed();
        } catch (Exception e) {
            // 异常处理记录日志..log.error(e);
            throw e;
        }
        System.out.println(methodRemark);
        System.out.println("@Around:执行目标方法之后...");
        return result;
    }

    // 获取方法的中文备注____用于记录用户的操作日志描述
    public static String getMthodRemark(ProceedingJoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String[] paramNameArr = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        System.out.println("=====调用方法的参数名称："+paramNameArr.toString());
        System. out.println("====调用" +methodName+"方法-开始！");
        Object[] arguments = joinPoint.getArgs();   //获得参数列表
        System.out.println("打印出方法调用时传入的参数，可以在这里通过添加参数的类型，进行一些简易逻辑处理和判断");
        if(arguments.length<=0){
            System.out.println("=== "+methodName+" 方法没有参数");
        }else{
            for(int i=0;i<arguments.length;i++){
                System.out.println("==== 参数   "+(i+1)+" : "+arguments[i]);
            }
        }

        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        String methode = "";
        for (Method m : method) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    LogEnable methodCache = m.getAnnotation(LogEnable.class);
                    Annotation[][] annotations = m.getParameterAnnotations();

                    methode = methodCache.moduleName();
                    break;
                }
            }
        }
        return methode;
    }
}
