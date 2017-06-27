package micro.service.users.controller;

import micro.service.users.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by Administrator on 2017/1/6.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Import(AppConfig.class)
public class TestUserController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUserService userService;

    @Test
    public void testUserController() throws Exception {
            this.mvc.perform(get("/users/1")).andExpect(status().isOk());
    }
}
