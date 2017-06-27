package micro.service.users.service;

import micro.service.users.domain.User;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
public interface IUserService {
    List<User> getUserList();

    boolean insertUser(User user);

    User getUser(Long id);

    boolean updateUser(Long id, User user);

    boolean deleteUser(Long id);

    List<User> getUserByName(String name);
}
