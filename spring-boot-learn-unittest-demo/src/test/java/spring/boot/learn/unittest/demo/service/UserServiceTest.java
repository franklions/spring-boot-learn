package spring.boot.learn.unittest.demo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import spring.boot.learn.unittest.demo.domain.User;
import spring.boot.learn.unittest.demo.mapper.UserMapper;


import java.util.ArrayList;
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
public class UserServiceTest {

    @MockBean
    UserMapper userMapper;

    @Autowired
    IUserService userService;

    User user;

    @Before
    public void initParam(){
        user = new User();
        user.setName("tom");
        user.setAge(18);
    }

    @Test
    public void createUser() throws Exception {
        when(this.userMapper.insert(anyObject())).thenReturn(1);
        Boolean result =  this.userService.createUser(user);
        assertTrue(result);
        verify(this.userMapper,times(1)).insert(anyObject());
    }

    @Test
    public void getAllUser() throws Exception {

        List<User> allUsers = new ArrayList<User>(){{add(user);add(user);add(user);add(user);add(user);}};
        when(this.userMapper.selectAllUser()).thenReturn(allUsers);
        List<User> result = this.userService.getAllUser();
        assertEquals(result, allUsers);
        verify(this.userMapper,times(1)).selectAllUser();
    }

    @Test
    public void getUserById() throws Exception {
        when(this.userMapper.selectByPrimaryKey(anyLong())).thenReturn(user);
        User result = this.userService.getUserById(1L);
        assertEquals(result,user);
        verify(this.userMapper,times(1)).selectByPrimaryKey(anyLong());
    }

    @Test
    public void updateUser() throws Exception {

        when(this.userMapper.updateByPrimaryKey(anyObject())).thenReturn(1);
        Boolean result = this.userService.updateUser(1L,user);

        assertTrue(result);
        verify(this.userMapper,times(1)).updateByPrimaryKey(anyObject());
    }

    @Test
    public void deleteUser() throws Exception {
        when(this.userMapper.deleteByPrimaryKey(anyLong())).thenReturn(1);

        Boolean result = this.userService.deleteUser(1L);

        assertTrue(result);
        verify(this.userMapper,times(1)).deleteByPrimaryKey(anyLong());
    }

    @Test
    public void getUserByName() throws Exception {
        List<User> allUsers = new ArrayList<User>(){{add(user);add(user);add(user);add(user);add(user);}};

        when(this.userMapper.selectAllUser()).thenReturn(allUsers);

        User result = this.userService.getUserByName("tom");
        System.out.printf("user:"+result);
        assertNotNull(result);

        verify(this.userMapper,times(1)).selectAllUser();
    }


}
