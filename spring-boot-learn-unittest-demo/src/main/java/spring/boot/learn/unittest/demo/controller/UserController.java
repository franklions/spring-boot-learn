package spring.boot.learn.unittest.demo.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.boot.learn.unittest.demo.domain.User;
import spring.boot.learn.unittest.demo.service.IUserService;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/8/14
 * @since Jdk 1.8
 */
@RestController
public class UserController {

    private Logger logger = LogManager.getLogger(getClass());

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService){
        this.userService = userService;
    }


    @RequestMapping(value="/users",method = RequestMethod.GET)
    public List<User> getAllUser(){
        logger.info("controller>>>>>>>>>>>>getUserList>>>>>>>>>>>>");
        return userService.getAllUser();
    }

    @RequestMapping(value="/users",method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user){
        boolean retval =userService.createUser(user);
        return retval?"success<"+user.getId()+">":"fail";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id,@ModelAttribute User user){
        boolean retval = userService.updateUser(id,user);
        return retval?"success":"fail";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id)
    {
        boolean retval = userService.deleteUser(id);
        return retval?"success":"fail";
    }
}
