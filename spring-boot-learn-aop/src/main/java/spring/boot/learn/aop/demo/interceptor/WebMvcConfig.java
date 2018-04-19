package spring.boot.learn.aop.demo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/18
 * @since Jdk 1.8
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    //增加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new HttpInterceptor())    //指定拦截器类
                // 排除配置
                .excludePathPatterns("/error")
                .excludePathPatterns("/login**")
                .addPathPatterns("/inter/**");        //指定该类拦截的url

        super.addInterceptors(registry);
    }
}
