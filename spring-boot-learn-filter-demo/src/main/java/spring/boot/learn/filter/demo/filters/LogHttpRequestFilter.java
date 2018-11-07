package spring.boot.learn.filter.demo.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.logging.IotLogger;
import com.iemylife.iot.webtoolkit.filter.RequestHeaderWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
    private static final String LOG_MESSAGE_KEY_REQUEST_METHOD="requestMethod";
    private static final String IOT_REQUEST_HEADER_TOKEN_KEY ="iotmsg-token";

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/prometric", "/error", "/autoconfig", "/mappings","/evn","/metrics","/health","/info")));

    ObjectMapper objectMapper;

    IotLogger logger;

    WebToolkitFilterProperties filterProperties;

    public LogHttpRequestFilter(IotLogger logger, ObjectMapper objectMapper, WebToolkitFilterProperties filterProperties){
        this.logger = logger;
        this.objectMapper = objectMapper;
        this.filterProperties = filterProperties;
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if(filterProperties.isEnable()) {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

            String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()).replaceAll("[/]+$", "");
            boolean allowedPath = ALLOWED_PATHS.contains(path);
            if (allowedPath) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                /**
                 * 加入跨域支持
                 */
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET,DELETE,PUT,OPTIONS");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,DeveloperId,Token,Scope,sessionId,ticketId");


                RequestHeaderWrapper request = new RequestHeaderWrapper(httpRequest);
                request.putHeader(IOT_REQUEST_HEADER_TOKEN_KEY, UUID.randomUUID().toString());

                try {


                    String requestUrl = request.getRequestURL().toString();
                    String requestMethod = request.getMethod();
                    Map paramMap = request.getParameterMap();

                    Map headMap = new HashMap();
                    Enumeration<String> em = request.getHeaderNames();

                    while (em.hasMoreElements()) {
                        String name = (String) em.nextElement();
                        String value = request.getHeader(name);
                        headMap.put(name, value);
                    }

                    Map logMap = new HashMap();
                    logMap.put(LOG_MESSAGE_KEY_REQUESTURL, requestUrl);
                    logMap.put(LOG_MESSAGE_KEY_REQUEST_METHOD, requestMethod);
                    logMap.put(LOG_MESSAGE_KEY_REQUEST_PARAMETER, paramMap);
                    logMap.put(LOG_MESSAGE_KEY_REQUEST_HEADER, headMap);


                    String logMessage = objectMapper.writeValueAsString(logMap);

                    logger.info(logMessage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                filterChain.doFilter(request, response);

            }
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    public void destroy() {

    }
}
