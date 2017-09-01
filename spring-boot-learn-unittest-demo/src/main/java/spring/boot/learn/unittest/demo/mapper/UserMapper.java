package spring.boot.learn.unittest.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spring.boot.learn.unittest.demo.domain.User;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/8/14
 * @since Jdk 1.8
 */
@Mapper
public interface UserMapper {
      int deleteByPrimaryKey(@Param("id") Long id);

      int insert(@Param("record") User record);

      User selectByPrimaryKey(@Param("id") Long id);

      int updateByPrimaryKey(@Param("record") User record);

    List<User> selectAllUser();
}
