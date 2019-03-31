package com.franklions.springMVC.servlet;

import com.franklions.springMVC.annotation.FLSAutowired;
import com.franklions.springMVC.annotation.FLSController;
import com.franklions.springMVC.annotation.FLSRequestMapping;
import com.franklions.springMVC.annotation.FLSService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-03-30
 * @since Jdk 1.8
 */
public class FLSDispatcherServlet extends HttpServlet {

    /**
     * 配置文件属性信息
     */
    private Properties contextConfig = new Properties();

    /**
     * 所有扫描到的类名称
     */
    private List<String> classNames = new ArrayList<>();

    /**
     * 初始化Bean对象
     */
    private Map<String,Object> ioc = new HashMap<>();

    /**
     * url和方法的映射
     */
    private Map<String,Method> handlerMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        uri = uri.replace(contextPath,"").replaceAll("/+","/");

        if(!handlerMapping.containsKey(uri)){
            resp.getWriter().write("Not Found!");
            return;
        }

        Map<String,String[]> params = req.getParameterMap();
        Method method = handlerMapping.get(uri);
        String beanName = method.getDeclaringClass().getSimpleName();
        Object instance = ioc.get(beanName);
        method.invoke(instance,new Object[]{req,resp,params.get("name")[0]});
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        /**
         * 1.加载配置文件
         */
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        /**
         * 2.解析配置文件，并且读取信息，完成扫描 scanPackage包下面的所有类
         */
        doScanner(contextConfig.getProperty("scanPackage"));
        /**
         * 3.初始到化刚刚扫描出的所有的类，并且将其放入到IOC容器之中
         */
        doInstance();
        /**
         * 4.完成自动化注入的工作，DI
         */
        doAutowired();
        /**
         * 5.初始化HandlerMapping,将URL和Method进行一对一的关联
         */
        initHandlerMapping();


    }

    private void initHandlerMapping() {
        if(ioc.isEmpty()){
            return;
        }

        for (Map.Entry<String,Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();

            if(!clazz.isAnnotationPresent(FLSController.class)){
                continue;
            }

            String baseUrl = "";
            if(clazz.isAnnotationPresent(FLSRequestMapping.class)){
                FLSRequestMapping requestMapping =  clazz.getAnnotation(FLSRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            Method[] methods = clazz.getMethods();
            for(Method method : methods){
                if(!method.isAnnotationPresent(FLSRequestMapping.class)){
                    continue;
                }

                FLSRequestMapping requestMapping =  clazz.getAnnotation(FLSRequestMapping.class);
                String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+","/");

                handlerMapping.put(url,method);

                System.out.println("Mapped:"+url + ","+method);
            }

        }

    }

    private void doAutowired() {

        if(ioc.isEmpty()){
            return;
        }

        for (Map.Entry<String,Object> entry : ioc.entrySet()){
            Field[] fields = entry.getValue().getClass().getDeclaredFields();

            for(Field field : fields){
                if(!field.isAnnotationPresent(FLSAutowired.class)){
                    continue;
                }

                FLSAutowired autowired = field.getAnnotation(FLSAutowired.class);
                String beanName = autowired.value().trim();
                if("".equals(beanName)){
                    beanName = field.getType().getName();
                }

                field.setAccessible(true);      //强制访问，只要加了注解，强制赋值

                try{
                    /**
                     * 给类中的字段进行赋值
                     */
                    field.set(entry.getValue(),ioc.get(beanName));
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private void doInstance() {

        if(classNames.isEmpty()){
            return;
        }

        //不是所有的类都要初始化
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);

                if (clazz.isAnnotationPresent(FLSController.class)) {

                    //默认的类名首字母小写做为key
                    String beanName = lowerFirstCase(clazz.getSimpleName());
                    if(ioc.containsKey(beanName)){
                        throw new Exception("The beanName is Definded.");
                    }
                    ioc.put(beanName,clazz.newInstance());
                } else if (clazz.isAnnotationPresent(FLSService.class)) {
                    FLSService service = clazz.getAnnotation(FLSService.class);
                    String beanName = service.value();
                    if("".equals(beanName.trim())){
                        beanName = lowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    if(ioc.containsKey(beanName)){
                        throw new Exception("The beanName is Definded.");
                    }
                    ioc.put(beanName,instance);

                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> i: interfaces){
                        if(ioc.containsKey(i.getName())){
                            throw new Exception("The beanName is Definded.");
                        }
                        ioc.put(i.getName(),instance);
                    }

                } else {
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String lowerFirstCase(String simpleName) {
        char[] nameChars = simpleName.toCharArray();
        nameChars[0] +=32;
        return String.valueOf(nameChars);

    }

    private void doScanner(String scanPackage) {
        //拿到的包名实际上就是一个字符串
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));

        File classDir = new File(url.getFile());

        for(File file : classDir.listFiles()){
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else{
                String className = (scanPackage + "."+ file.getName().replace(".class",""));
                classNames.add(className);
            }
        }

    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
