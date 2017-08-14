package spring.boot.learn.unittest.demo.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.learn.unittest.demo.domain.User;
import spring.boot.learn.unittest.demo.mapper.UserMapper;
import spring.boot.learn.unittest.demo.service.IUserService;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/8/14
 * @since Jdk 1.8
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public Boolean createUser(User user) {
        return this.userMapper.insert(user) > 0;
    }

    @Override
    public List<User> getAllUser() {
        return this.userMapper.selectAllUser();
    }

    @Override
    public User getUserById(Long id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean updateUser(Long id, User user) {
        return this.userMapper.updateByPrimaryKey(user) > 0;
    }

    @Override
    public Boolean deleteUser(Long id) {
        return this.userMapper.deleteByPrimaryKey(id) >0;
    }
}
