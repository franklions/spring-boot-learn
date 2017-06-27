package micro.service.users.service.impl;

import com.github.pagehelper.PageHelper;
import micro.service.users.domain.User;
import micro.service.users.mapper.UserMapper;
import micro.service.users.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public List<User> getUserList() {
        logger.info("UserServiceImpl>>>>>>>>>>>>getUserList>>>>>>>>>>>>");
        PageHelper.startPage(1,2);
        return userMapper.getAllList();
    }

    @Override
    public boolean insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User getUser(Long id) {
        return userMapper.getUser(id);
    }

    @Override
    public boolean updateUser(Long id, User user) {
        return userMapper.update(id,user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return userMapper.delete(id);
    }

    @Override
    public List<User> getUserByName(String name) {
        return userMapper.getUserByName(name);
    }
}
