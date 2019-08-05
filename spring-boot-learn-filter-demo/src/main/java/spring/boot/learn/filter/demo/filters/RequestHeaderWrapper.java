package spring.boot.learn.filter.demo.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/14
 * @since Jdk 1.8
 */
public class RequestHeaderWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public RequestHeaderWrapper(HttpServletRequest request) {
        super(request);

        this.customHeaders = new HashMap<>();
    }

    public void putHeader(String name,String value){

        String headerKey = getHeader(name);
        if(headerKey== null || headerKey.length() < 1) {
            this.customHeaders.putIfAbsent(name, value);
        }
    }

    @Override
    public String getHeader(String name) {
        // check the custom headers first
        String headerValue = customHeaders.get(name);

        if (headerValue != null){
            return headerValue;
        }

        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        // create a set of the custom header names
        Set<String> set = new HashSet<String>(customHeaders.keySet());

        // now add the headers from the wrapped request object
        @SuppressWarnings("unchecked")
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            // add the names of the request headers into the list
            String n = e.nextElement();
            set.add(n);
        }

        // create an enumeration from the set and return
        return Collections.enumeration(set);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (customHeaders.containsKey(name)) {
            values.add(customHeaders.get(name));
        }
        return Collections.enumeration(values);
    }
}
