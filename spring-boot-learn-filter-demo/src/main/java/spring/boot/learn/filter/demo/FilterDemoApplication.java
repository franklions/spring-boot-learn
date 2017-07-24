package spring.boot.learn.filter.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.logging.IotLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import spring.boot.learn.filter.demo.filters.HTTPBasicAuthorizeAttributeFilter;
import spring.boot.learn.filter.demo.filters.LogHttpRequestFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/13
 * @since Jdk 1.8
 */
@SpringBootApplication
//@ServletComponentScan
public class FilterDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilterDemoApplication.class,args);
    }

//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private IotLogger logger;
//
//    @Bean
//    public FilterRegistrationBean httpfilterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        HTTPBasicAuthorizeAttributeFilter httpBasicFilter = new HTTPBasicAuthorizeAttributeFilter();
//        registrationBean.setFilter(httpBasicFilter);
//        List<String> urlPatterns = new ArrayList<String>();
//        urlPatterns.add("/*");
//        registrationBean.setUrlPatterns(urlPatterns);
//        return registrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean logFilterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        LogHttpRequestFilter httpRequestFilter = new LogHttpRequestFilter(logger,objectMapper);
//        registrationBean.setFilter(httpRequestFilter);
//        List<String> urlPatterns = new ArrayList<String>();
//        urlPatterns.add("/*");
//        registrationBean.setUrlPatterns(urlPatterns);
//        return registrationBean;
//    }
}
