package spring.boot.learn.filter.demo.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.logging.IotLogger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author flsh
 * @version 1.0
 * @description
 * 日志过滤器，对Web请求头获取并输出到应用日志
 * @date 2017/6/13
 * @since Jdk 1.8
 */
public class LogHttpRequestFilter implements Filter {

    private static final String LOG_MESSAGE_KEY_REQUESTURL="requestUrl";
    private static final String LOG_MESSAGE_KEY_REQUEST_PARAMETER="requestParameter";
    private static final String LOG_MESSAGE_KEY_REQUEST_HEADER="requestHeader";

    ObjectMapper objectMapper;

    IotLogger logger;

    public LogHttpRequestFilter(IotLogger logger, ObjectMapper objectMapper){
        this.logger = logger;
        this.objectMapper = objectMapper;
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            String requestUrl = httpRequest.getRequestURL().toString();
            Map paramMap = httpRequest.getParameterMap();

            Map headMap = new HashMap();
            Enumeration<String> em = httpRequest.getHeaderNames();
            while (em.hasMoreElements()) {
                String name = (String) em.nextElement();
                String value = httpRequest.getHeader(name);
                headMap.put(name, value);
            }

            Map logMap = new HashMap();
            logMap.put(LOG_MESSAGE_KEY_REQUESTURL, requestUrl);
            logMap.put(LOG_MESSAGE_KEY_REQUEST_PARAMETER, paramMap);
            logMap.put(LOG_MESSAGE_KEY_REQUEST_HEADER, headMap);

            String logMessage = objectMapper.writeValueAsString(logMap);

            logger.info(logMessage);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
