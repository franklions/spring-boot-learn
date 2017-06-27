package micro.service.users.mapper;

import micro.service.users.controller.AppConfig;
import micro.service.users.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/1/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserMapper {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Rollback
    @Transactional
    public void testUserMapper(){
            User user = this.userMapper.getUser(4L);
            assertEquals("tom",user.getName());
    }

}
