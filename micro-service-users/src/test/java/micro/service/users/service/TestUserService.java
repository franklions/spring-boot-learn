package micro.service.users.service;

import micro.service.users.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/1/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserService {

    @Autowired
    private IUserService userService;

    @Test
    public void testUserService(){
        User user = this.userService.getUser(4L);
        assertEquals("tom",user.getName());
    }
}
