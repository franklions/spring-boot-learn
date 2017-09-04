package spring.boot.learn.unittest.demo.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import spring.boot.learn.unittest.demo.domain.User;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/8/14
 * @since Jdk 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper mapper;

    @Test
    @Transactional
    @Rollback
    public void deleteByPrimaryKey() throws Exception {

        Integer retval =  this.mapper.deleteByPrimaryKey(1L);
        assertTrue(retval.intValue() >0);
    }

    @Test
    @Transactional
    @Rollback
    public void insert() throws Exception {
        User newUser = new User();
        newUser.setName("jerry");
        newUser.setAge(7);

        Integer retval = this.mapper.insert(newUser);
        //验证插入成功
        assertTrue(retval.intValue() > 0);
        //验证返回ID
        assertNotNull(newUser.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void selectByPrimaryKey() throws Exception {

        User user = this.mapper.selectByPrimaryKey(1L);
        assertNotNull(user);
    }

    @Test
    @Transactional
    @Rollback
    public void updateByPrimaryKey() throws Exception {

        User upUser = new User();
        upUser.setId(1L);
        upUser.setAge(19);

        int retval  = this.mapper.updateByPrimaryKey(upUser);
        assertTrue(retval >0);
    }

    @Test
    @Transactional
    @Rollback
    public void selectAllUser() throws Exception {

        List<User> allUsers  = this.mapper.selectAllUser();

        assertNotNull(allUsers);
    }
}
