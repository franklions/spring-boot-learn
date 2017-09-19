package spring.boot.learn.unittest.demo.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value="/users",method = RequestMethod.GET)
    public List<User> getAllUser(){
        logger.info("controller>>>>>>>>>>>>getUserList>>>>>>>>>>>>");
        return userService.getAllUser();
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value="/users",method = RequestMethod.POST)
    public String postUser(@RequestBody User user){
        boolean retval =userService.createUser(user);
        return retval?"success<"+user.getId()+">":"fail";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/users/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的name来获取用户详细信息")
    @ApiImplicitParam(name = "name", value = "用户名称", required = true, dataType = "String")
    @RequestMapping(value = "/users/search/{name}",method = RequestMethod.GET)
    public User getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value="/users/{id}",method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id,@RequestBody User user){
        boolean retval = userService.updateUser(id,user);
        return retval?"success":"fail";
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/users/{id}",method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id)
    {
        boolean retval = userService.deleteUser(id);
        return retval?"success":"fail";
    }
}
