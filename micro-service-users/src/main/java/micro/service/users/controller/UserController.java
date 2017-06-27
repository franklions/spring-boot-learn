package micro.service.users.controller;

import micro.service.users.domain.User;
import micro.service.users.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
@RestController
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private IUserService userService;

    private Logger logger = LogManager.getLogger(getClass());

    @RequestMapping(value="/",method = RequestMethod.GET)
    public List<User> getUserList(){
        logger.info("controller>>>>>>>>>>>>getUserList>>>>>>>>>>>>");
        return userService.getUserList();
    }

    @RequestMapping(value="/",method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user){
        boolean retval =userService.insertUser(user);
        return retval?"success<"+user.getId()+">":"fail";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
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
