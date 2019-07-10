/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package spring.boot.learn.filter.demo.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", filterName = "httpBasicAuthorizedFilter")
public class HttpBasicAuthorizedFilter implements Filter {

    @Value("${rocketmq.basic.security.username:admin}")
    private String username;
    @Value("${rocketmq.basic.security.password:lwkj0308}")
    private String password;

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"rocketmq\"");

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String auth = httpRequest.getHeader("Authorization");
        if(auth == null || auth.trim().equals("")) {
            createUnAuthResponse(httpResponse);
            return ;
        }

        String[] authArr = auth.split(" ");

        if(authArr == null || authArr.length <2) {
            createUnAuthResponse(httpResponse);
            return ;
        }

        String userStr = new String( new BASE64Decoder().decodeBuffer(authArr[1].trim()));
        if(userStr ==null || userStr.length()<1){
            createUnAuthResponse(httpResponse);
            return ;
        }

        String[] userInfo = userStr.split(":");

        if(userInfo == null || userInfo.length <2) {
            createUnAuthResponse(httpResponse);
            return ;
        }

        if(!username.equals(userInfo[0]) || !password.equals(userInfo[1])){
            createUnAuthResponse(httpResponse);
            return ;
        }

        chain.doFilter(request, response);

    }

    private void createUnAuthResponse(HttpServletResponse httpResponse) {
        httpResponse.setStatus(401);
        httpResponse.setHeader("Cache-Control", "no-store");
        httpResponse.setDateHeader("Expires", 0);
        httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"rocketmq\"");
    }

    @Override
    public void destroy() {
    }
}
