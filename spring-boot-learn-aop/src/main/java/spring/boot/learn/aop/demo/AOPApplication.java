package spring.boot.learn.aop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import spring.boot.learn.aop.demo.aspect.SpringAspectDemo;
import spring.boot.learn.aop.demo.filter.HTTPBasicAuthorizeAttributeFilter;
import spring.boot.learn.aop.demo.interceptor.HttpInterceptor;
import spring.boot.learn.aop.demo.service.AdviceService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/16
 * @since Jdk 1.8
 */
@SpringBootApplication
public class AOPApplication  implements CommandLineRunner{

    @Autowired
    AdviceService adviceService;

    public static void main(String[] args) {
        SpringApplication.run(AOPApplication.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("running......");
        adviceService.manyAdvices("test","runtest");
    }

    /**
     * 注意：路径和扩展名匹配无法同时设置，比如下面的三个<url-pattern>都是非法的，如果设置，启动tomcat服务器会报错。
     * url-pattern>/kata/*.jsp</url-pattern>
     * <url-pattern>/*.jsp</url-pattern>
     * <url-pattern>he*.jsp</url-pattern>
     * 另外注意：<url-pattern>/aa/*\/bb</url-pattern>
     * 这个是精确匹配，url必须是 /aa/*\/bb，这里的*不是通配的含义
    * 五、匹配任意的url
    * 如果<url-pattern>配置成如下两种的任意一种
    * <url-pattern>/</url-pattern>
    * <url-pattern>/*</url-pattern>
    * 则所有的url都可以被匹配上。其中/*是路径匹配，只是路径就是/。
     * @return
     */
    @Bean
    public FilterRegistrationBean httpFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPBasicAuthorizeAttributeFilter httpBasicFilter = new HTTPBasicAuthorizeAttributeFilter();
        registrationBean.setFilter(httpBasicFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/value/*");        //路径匹配
        urlPatterns.add("/kata/detail.html");   //精确匹配
        urlPatterns.add("*.jsp");                //扩展名匹配
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
