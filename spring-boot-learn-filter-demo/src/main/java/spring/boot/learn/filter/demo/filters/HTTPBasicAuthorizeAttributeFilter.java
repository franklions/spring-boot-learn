package spring.boot.learn.filter.demo.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import sun.misc.BASE64Decoder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/13
 * @since Jdk 1.8
 */
public class HTTPBasicAuthorizeAttributeFilter implements Filter {

    private static String Name = "test";
    private static String Password = "test";

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

        ResultStatusCode resultStatusCode = checkHTTPBasicAuthorize(request);
//        ServletResponse newResponse = createServletRResponse(response);
        if (resultStatusCode != ResultStatusCode.OK)
        {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json; charset=utf-8");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);


            httpResponse.getWriter().write("\"没有权限\"");
            return;
        }
        else
        {
            chain.doFilter(request, response);
        }
    }

/*
    private ServletResponse createServletRResponse(ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse)response;

        return httpResponse;
    }
*/

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    private ResultStatusCode checkHTTPBasicAuthorize(ServletRequest request)
    {
        try
        {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            printHttpServletRequest(httpRequest);
            String auth = httpRequest.getHeader("Authorization");
            if ((auth != null) && (auth.length() > 6))
            {
                System.out.println("AUTH = [" + auth + "]");
                String HeadStr = auth.substring(0, 5).toLowerCase();
                if (HeadStr.compareTo("basic") == 0)
                {
                    auth = auth.substring(6, auth.length());
                    String decodedAuth = getFromBASE64(auth);
                    if (decodedAuth != null)
                    {
                        String[] UserArray = decodedAuth.split(":");

                        if (UserArray != null && UserArray.length == 2)
                        {
                            if (UserArray[0].compareTo(Name) == 0
                                    && UserArray[1].compareTo(Password) == 0)
                            {
                                return ResultStatusCode.OK;
                            }
                        }
                    }
                }
            }
            return ResultStatusCode.PERMISSION_DENIED;
        }
        catch(Exception ex)
        {
            return ResultStatusCode.PERMISSION_DENIED;
        }

    }

    private void printHttpServletRequest(HttpServletRequest request) {
        System.out.println("getRequestURL: "+request.getRequestURL());
        System.out.println("getRequestURI: "+request.getRequestURI());
        System.out.println("getQueryString: "+request.getQueryString());
        System.out.println("getRemoteAddr: "+request.getRemoteAddr());
        System.out.println("getRemoteHost: "+request.getRemoteHost());
        System.out.println("getRemotePort: "+request.getRemotePort());
        System.out.println("getRemoteUser: "+request.getRemoteUser());
        System.out.println("getLocalAddr: "+request.getLocalAddr());
        System.out.println("getLocalName: "+request.getLocalName());
        System.out.println("getLocalPort: "+request.getLocalPort());
        System.out.println("getMethod: "+request.getMethod());
        System.out.println("-------request.getParamterMap()-------");
        //得到请求的参数Map，注意map的value是String数组类型
        Map map = request.getParameterMap();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String[] values = (String[]) map.get(key);
            for (String value : values) {
                System.out.println(key+"="+value);
            }
        }
        System.out.println("--------request.getHeader()--------");
        //得到请求头的name集合
        Enumeration<String> em = request.getHeaderNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getHeader(name);
            System.out.println(name+"="+value);
        }
    }

    private String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
}
