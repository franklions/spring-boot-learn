package spring.boot.learn.filter.demo.filters;

import andon.iot.entity.po.IotUser;
import andon.iot.entity.ResponseErrorResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.spring.http.ErrorResult;
import org.apache.logging.log4j.core.config.Order;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/7/18
 * @since Jdk 1.8
 */
@WebFilter(filterName = "httpControllerAuthorizeFilter", urlPatterns = "/user/*")
@Order(Integer.MIN_VALUE)
public class HttpControllerAuthorizeFilter extends OncePerRequestFilter {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${iot.zuul-url}")
    String zuulUrl;

    private static final List<String> ignoreUrls = new ArrayList() {{
        add("/user/authorize");
    }};

    private static String UserServiceUrl = "/v0/user_service";

    private static String DeveloperServiceUrl = "/v0/developer";

    public HttpControllerAuthorizeFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //获取请求的url
            String uri = request.getRequestURI();

            if (uri == null || uri.trim().length() < 1) {
                ErrorResult errorResult = new ErrorResult(404, "Not found url.");
                responseErrorHandler(response, HttpServletResponse.SC_NOT_FOUND, errorResult);
                return;
            }
            //忽略的url，不对这些url进行验证
            if (ignoreUrls.contains(uri)) {
                filterChain.doFilter(request, response);
                return;
            }

            //根据路径中的第二段拿到uid
            String[] uriSplit = uri.split("/");

            if (uriSplit.length < 3) {
                ErrorResult errorResult = new ErrorResult(400, "url address error.");
                responseErrorHandler(response, HttpServletResponse.SC_BAD_REQUEST, errorResult);
                return;
            }

            String uid = uri.split("/")[2];
            if (uid == null || uid.trim().length() < 1) {
                ErrorResult errorResult = new ErrorResult(400, "uid can not null.");
                responseErrorHandler(response, HttpServletResponse.SC_BAD_REQUEST, errorResult);
                return;
            }
            //拿到header中的authorize参数
            String authorization = request.getHeader("x-Authorization");

            if (authorization == null || authorization.trim().length() < 1) {
                ErrorResult errorResult = new ErrorResult(400, "x-Authorization can not null.");
                responseErrorHandler(response, HttpServletResponse.SC_BAD_REQUEST, errorResult);
                return;
            }


            String[] authInfo = authorization.split(" ");
            if (authInfo == null || authInfo.length < 2) {
                ErrorResult errorResult = new ErrorResult(400, "x-Authorization argument error.");
                responseErrorHandler(response, HttpServletResponse.SC_BAD_REQUEST, errorResult);
                return;
            }

            ErrorResult errorResult = null;
            String authToken = new String(Base64.decodeBase64(authInfo[1]));
            if (authToken == null || authToken.trim().length() < 1) {
                errorResult = new ErrorResult(400, "x-Authorization argument error.");
                responseErrorHandler(response, HttpServletResponse.SC_BAD_REQUEST, errorResult);
                return;
            }
            String[] authTokenInfo = authToken.split(":");
            if (authTokenInfo == null || authTokenInfo.length < 2) {
                errorResult = new ErrorResult(400, "x-Authorization argument error.");
                responseErrorHandler(response, HttpServletResponse.SC_BAD_REQUEST, errorResult);
                return;
            }

            switch (authInfo[0].toLowerCase()) {
                case "user":
                    if (authTokenInfo == null || authTokenInfo.length < 3) {
                        errorResult = new ErrorResult(400, "x-Authorization argument error.");
                        responseErrorHandler(response, HttpServletResponse.SC_BAD_REQUEST, errorResult);
                        return;
                    }
                    errorResult = requestUserServiceAuth(uid, authTokenInfo[0], authTokenInfo[1], authTokenInfo[2]);
                    if (errorResult != null) {
                        //登录失败返回失败原因给调用者
                        Double errorCode = errorResult.getErrorCode();
                        //uid不存在
                        if ( errorCode ==404.1 || errorCode == 404) {
                            errorResult = new ErrorResult(404102, "Not found user.");
                            responseErrorHandler(response, HttpServletResponse.SC_NOT_FOUND, errorResult);
                            return;
                        }

                        //token不存在
                        if (errorCode == 404.2) {
                            errorResult = new ErrorResult(404103, "Not found token.");
                            responseErrorHandler(response, HttpServletResponse.SC_NOT_FOUND, errorResult);
                            return;
                        }

                        //token认证失败
                        if (401==errorCode || 401.3==errorCode || 401.2==errorCode) {
                            errorResult = new ErrorResult(401101, "user Unauthorized.");
                            responseErrorHandler(response, HttpServletResponse.SC_UNAUTHORIZED, errorResult);
                            return;
                        }

                        responseErrorHandler(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorResult);
                        return;
                    }
                    break;
                case "token":
                    errorResult = requestDeveloperServiceAuth("token", authTokenInfo[0], authTokenInfo[1]);
                    if (errorResult != null) {
                        Double errorCode = errorResult.getErrorCode();

                        //app不存在
                        if (404.1==errorCode || errorCode==404) {
                            errorResult = new ErrorResult(404102, "Not found developer.");
                            responseErrorHandler(response, HttpServletResponse.SC_NOT_FOUND, errorResult);
                            return;
                        }

                        //token不存在
                        if (404.2==errorCode) {
                            errorResult = new ErrorResult(404103, "Not found token.");
                            responseErrorHandler(response, HttpServletResponse.SC_NOT_FOUND, errorResult);
                            return;
                        }

                        //token认证失败
                        if (401==errorCode || 401.3==errorCode) {
                            errorResult = new ErrorResult(401101, "App Unauthorized.");
                            responseErrorHandler(response, HttpServletResponse.SC_UNAUTHORIZED, errorResult);
                            return;
                        }

                        //token认证失败
                        if (409.1==errorCode) {
                            errorResult = new ErrorResult(401101, "App Unauthorized.");
                            responseErrorHandler(response, HttpServletResponse.SC_UNAUTHORIZED, errorResult);
                            return;
                        }

                        responseErrorHandler(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorResult);
                        return;
                    }
                    break;
                case "secret":
                    errorResult = requestDeveloperServiceAuth("secret", authTokenInfo[0], authTokenInfo[1]);
                    if (errorResult != null) {
                        Double errorCode = errorResult.getErrorCode();
                        //app不存在
                        if (404.1==errorCode) {
                            errorResult = new ErrorResult(404102, "Not found developer.");
                            responseErrorHandler(response, HttpServletResponse.SC_NOT_FOUND, errorResult);
                            return;
                        }

                        //token不存在
                        if (404.2==errorCode) {
                            errorResult = new ErrorResult(404103, "Not found secret.");
                            responseErrorHandler(response, HttpServletResponse.SC_NOT_FOUND, errorResult);
                            return;
                        }

                        //token认证失败
                        if (401==errorCode || 401.3==errorCode) {
                            errorResult = new ErrorResult(401101, "App Unauthorized.");
                            responseErrorHandler(response, HttpServletResponse.SC_UNAUTHORIZED, errorResult);
                            return;
                        }

                        //token认证失败
                        if (409.1==errorCode) {
                            errorResult = new ErrorResult(401101, "App Unauthorized.");
                            responseErrorHandler(response, HttpServletResponse.SC_UNAUTHORIZED, errorResult);
                            return;
                        }
                        responseErrorHandler(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorResult);
                        return;
                    }
                    break;
                default:
                    errorResult = new ErrorResult(400, "x-Authorization argument error.");
                    responseErrorHandler(response, HttpServletResponse.SC_BAD_REQUEST, errorResult);
                    return;
            }

            //登录成功获取用户加入到header中
            IotUser userInfo = requestUserServiceGetUser(uid);
            if (userInfo == null) {
                //用户不存在
                errorResult = new ErrorResult(404102, "Not found user.");
                responseErrorHandler(response, HttpServletResponse.SC_NOT_FOUND, errorResult);
                return;
            }

            userInfo.setAppId(authTokenInfo[0]);
            RequestHeaderWrapper requestHeaderWrapper = new RequestHeaderWrapper(request);
            requestHeaderWrapper.setAttribute("user", userInfo);
            filterChain.doFilter(requestHeaderWrapper, response);
            return;

        } catch (Exception e) {
            logger.error("HttpFilter Error:" + e.getMessage(), e);
            ErrorResult errorResult = new ErrorResult(500, "Internal server error.");
            responseErrorHandler(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorResult);
            return;
        }
    }

    /**
     * 调用开发者服务验证Appid
     *
     * @param type
     * @param appId
     * @param pwd
     * @return
     * @throws IOException
     */
    private ErrorResult requestDeveloperServiceAuth(String type, String appId, String pwd) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("appId", appId);

        String authUrl;
        if ("token".equals(type)) {
            authUrl = "/apps/tokens/verify";
            headers.add("appToken", pwd);
        } else {
            authUrl = "/apps/secrets/verify";
            headers.add("appSecret", pwd);
        }

        HttpEntity httpEntity = new HttpEntity(null, headers);

        ErrorResult errorResult = null;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(zuulUrl + DeveloperServiceUrl + authUrl,
                    HttpMethod.GET, httpEntity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return null;
            } else {
                return new ErrorResult(responseEntity.getStatusCodeValue(), responseEntity.getBody());
            }
        } catch (HttpClientErrorException cex) {
            switch (cex.getStatusCode()) {
                case BAD_REQUEST:
                case NOT_FOUND:
                case UNAUTHORIZED:
                case CONFLICT:
                    try {
                        ResponseErrorResult respErrorResult = objectMapper.readValue(cex.getResponseBodyAsString(), ResponseErrorResult.class);
                        errorResult = respErrorResult.getErrorResult();
                    } catch (JsonProcessingException jpe) {
                        errorResult = new ErrorResult(cex.getStatusCode().value(), cex.getResponseBodyAsString());
                    }
                    break;
                default:
                    return new ErrorResult(cex.getStatusCode().value(), cex.getResponseBodyAsString());
            }
        } catch (HttpServerErrorException sex) {
            try {
                ResponseErrorResult respErrorResult = objectMapper.readValue(sex.getResponseBodyAsString(), ResponseErrorResult.class);
                errorResult = respErrorResult.getErrorResult();
            } catch (JsonProcessingException jpe) {
                return new ErrorResult(sex.getStatusCode().value(), sex.getResponseBodyAsString());
            }
        } catch (RestClientException rcex) {
            //这个是未知的状态码异常
            return new ErrorResult(500d, rcex.getMessage());
        }

        return errorResult;
    }

    private void responseErrorHandler(HttpServletResponse response, Integer httpStatusCode, ErrorResult errorResult) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(httpStatusCode);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResult));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("HttpFilter response error:" + e.getMessage(), e);
        }

    }

    /**
     * 调用用户服务获取用户信息
     *
     * @param uid
     * @return
     */
    private IotUser requestUserServiceGetUser(String uid) {

        ResponseEntity<IotUser> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(zuulUrl + UserServiceUrl + "/users/{uid}", HttpMethod.GET, null, IotUser.class, uid);
            if (responseEntity == null || responseEntity.getBody() == null || responseEntity.getBody().getId() < 1) {
                return null;
            } else {
                return responseEntity.getBody();
            }
        } catch (HttpStatusCodeException e) {
            logger.warn("获取用户信息失败：" + e.getResponseBodyAsString(), e);
        } catch (Exception ex) {
            logger.error("获取用户信息发生异常：" + ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * 请求用户服务验证用户token是否正确
     *
     * @param uid
     * @param appid
     * @param token
     * @param clientType
     * @return 正确返回null错误返回相应的 errorResult
     */
    private ErrorResult requestUserServiceAuth(String uid, String appid, String token, String clientType) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("uid", uid);
        headers.set("token", token);
        headers.set("clientType", clientType);
        headers.set("appid", appid);
        HttpEntity httpEntity = new HttpEntity(null, headers);

        ErrorResult errorResult = null;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(zuulUrl + UserServiceUrl + "/users/tokens/authorize",
                    HttpMethod.GET, httpEntity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return null;
            } else {
                return new ErrorResult(responseEntity.getStatusCodeValue(), responseEntity.getBody());
            }
        } catch (HttpClientErrorException cex) {
            switch (cex.getStatusCode()) {
                case BAD_REQUEST:
                case NOT_FOUND:
                case UNAUTHORIZED:
                    try {
                        ResponseErrorResult respErrorResult = objectMapper.readValue(cex.getResponseBodyAsString(), ResponseErrorResult.class);
                        errorResult = respErrorResult.getErrorResult();
                    } catch (JsonProcessingException jpe) {
                        errorResult = new ErrorResult(cex.getStatusCode().value(), cex.getResponseBodyAsString());
                    }
                    break;
                default:
                    return new ErrorResult(cex.getStatusCode().value(), cex.getResponseBodyAsString());
            }
        } catch (HttpServerErrorException sex) {
            try {
                ResponseErrorResult respErrorResult = objectMapper.readValue(sex.getResponseBodyAsString(), ResponseErrorResult.class);
                errorResult = respErrorResult.getErrorResult();
            } catch (JsonProcessingException jpe) {
                return new ErrorResult(sex.getStatusCode().value(), sex.getResponseBodyAsString());
            }
        } catch (RestClientException rcex) {
            //这个是未知的状态码异常
            return new ErrorResult(500d, rcex.getMessage());
        }

        return errorResult;
    }
}
