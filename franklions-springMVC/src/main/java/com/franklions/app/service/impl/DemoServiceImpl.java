package com.franklions.app.service.impl;

import com.franklions.app.service.IDemoService;
import com.franklions.springMVC.annotation.FLSService;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-03-30
 * @since Jdk 1.8
 */
@FLSService
public class DemoServiceImpl implements IDemoService {
    @Override
    public String get(String name) {
        return "hello "+name;
    }
}
