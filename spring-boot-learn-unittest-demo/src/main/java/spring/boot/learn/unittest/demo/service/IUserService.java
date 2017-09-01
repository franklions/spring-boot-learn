package spring.boot.learn.unittest.demo.service;

import spring.boot.learn.unittest.demo.domain.User;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/8/14
 * @since Jdk 1.8
 */
public interface IUserService {
    Boolean createUser(User user);

    List<User> getAllUser();

    User    getUserById(Long id);

    Boolean updateUser(Long id, User user);

    Boolean deleteUser(Long id);

    User getUserByName(String name);
}
