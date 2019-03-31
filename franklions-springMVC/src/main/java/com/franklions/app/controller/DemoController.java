package com.franklions.app.controller;

import com.franklions.app.service.IDemoService;
import com.franklions.springMVC.annotation.FLSAutowired;
import com.franklions.springMVC.annotation.FLSController;
import com.franklions.springMVC.annotation.FLSRequestMapping;
import com.franklions.springMVC.annotation.FLSRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-03-30
 * @since Jdk 1.8
 */
@FLSController
@FLSRequestMapping("/api")
public class DemoController {

    @FLSAutowired
    private IDemoService demoService;

    @FLSRequestMapping("/query.json")
    public void query(HttpServletRequest req, HttpServletResponse resp,
                      @FLSRequestParam("name") String name){
        String result = demoService.get(name);

        try {
            resp.getWriter().write(result);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
