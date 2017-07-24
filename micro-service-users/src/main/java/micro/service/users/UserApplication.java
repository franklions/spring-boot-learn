package micro.service.users;

import com.github.pagehelper.PageInterceptor;
import micro.service.users.mapper.CityMapper;
import micro.service.users.mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * Created by Administrator on 2016/12/1.
 */
@SpringBootApplication
@MapperScan("micro.service.users.mapper")
public class UserApplication implements CommandLineRunner {

//    @Autowired
//    private IPlatformLogger logger;
    private Logger logger = LogManager.getLogger(getClass());

//    private Logger logger = CustomLogFactory.createLogger(getClass().getName());

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }

    @Override
    public void run(String... strings) throws Exception {

        logger.info(">>>>>>>>>>>>>服务开始初始化>>>>>>>>>>");

//        logger.always(">>>>>>>>>>>>>>>>>always message>>>>>>>>>>>>>>");

//        PageHelper.startPage(1, 2);
//        List<City> countries = cityMapper.selectAll();
//        System.out.println( countries);

//        logger.info(">>>>>>>>>>>>>micro:"+this.cityMapper.findByState("CA"));

//        User newUser =new User("jerry",88);
//        this.userMapper.insert(newUser);
//        List<User> users = this.userMapper.getAllList();
//        for (User user:users) {
//            logger.info(user);
//        }
//        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>插入新用户："+newUser);
//        User u =this.userMapper.getUser(newUser.getId());
//        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>获取新插入用户:" + newUser);
//        if(u != null ){
//            u.setName("tom");
//            u.setAge(90);
//            this.userMapper.update(u.getId(),u);
//        }
//        users = this.userMapper.getAllList();
//        for (User user:users) {
//            logger.info(user);
//        }
//        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>修改用户>>>>>>>>>>>>>>>>>>>>>>>>");
//        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + this.userMapper.getUserByName("t"));
//        this.userMapper.delete(newUser.getId());
//        users = this.userMapper.getAllList();
//        for (User user:users) {
//            logger.info(user);
//        }
//
//        logger.info(">>>>>>>>>>>> 删除用户>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"  );
    }

    @Bean
    public PageInterceptor pageInterceptor(){
        PageInterceptor  interceptor = new PageInterceptor();
        Properties properties = new Properties();
        interceptor.setProperties(properties);

        return interceptor;
    }

}
