package micro.service.users.mapper;

import micro.service.users.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public interface UserMapper {
    User getUser(@Param("id") Long id);

    List<User> getAllList();

    boolean insert(@Param("user") User user);

    boolean update(@Param("key") Long id, @Param("user") User user);

    boolean delete(@Param("id") Long id);

    List<User> getUserByName(@Param("name") String name);
}
