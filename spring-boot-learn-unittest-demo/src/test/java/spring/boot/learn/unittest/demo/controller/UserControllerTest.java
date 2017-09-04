package spring.boot.learn.unittest.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import spring.boot.learn.unittest.demo.domain.User;
import spring.boot.learn.unittest.demo.service.IUserService;

import javax.sql.DataSource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/8/14
 * @since Jdk 1.8
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    DataSource dataSource;

    @MockBean
    IUserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    User user;

    @Before
    public  void initParams(){
         user = new User();
        user.setName("tom");
        user.setAge(10);
    }

    @Test
    public void getAllUser() throws Exception {
    }

    @Test
    public void postUser() throws Exception {

        String requestBody = mapper.writeValueAsString(user);

        when(this.userService.createUser(anyObject())).thenReturn(true);

        MockHttpServletResponse response = this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(this.userService).createUser(anyObject());
    }

    @Test
    public void getUser() throws Exception {

        when(this.userService.getUserById(anyLong())).thenReturn(user);

        MockHttpServletResponse response = this.mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse();

        System.out.println("response:"+response.getContentAsString());
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        verify(this.userService).getUserById(anyLong());
    }

    @Test
    public void getUserByName() throws Exception {

        when(this.userService.getUserByName(anyString())).thenReturn(user);

        MockHttpServletResponse response = this.mockMvc.perform(get("/users/search/tom")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse();

        System.out.println("response:"+response.getContentAsString());
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        verify(this.userService).getUserByName(anyString());
    }

    @Test
    public void putUser() throws Exception {

        user.setName("jerry");
        when(this.userService.updateUser(anyLong(),anyObject())).thenReturn(true);

        MockHttpServletResponse response = this.mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(user)))
                .andReturn().getResponse();

        System.out.println("response:"+response.getContentAsString());
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        verify(this.userService).updateUser(anyLong(),anyObject());
    }

    @Test
    public void deleteUser() throws Exception {

        when(this.userService.deleteUser(anyLong())).thenReturn(true);

        MockHttpServletResponse response = this.mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse();

        System.out.println("response:"+response.getContentAsString());
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        verify(this.userService).deleteUser(anyLong());
    }
}
