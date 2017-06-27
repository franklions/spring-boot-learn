package micro.service.users.mapper;

import micro.service.users.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */
@Mapper
public interface CityMapper {
    @Select("select * from city where state = #{state}")
    City findByState(@Param("state") String state);

    @Select("select * from city ")
    List<City> selectAll();
}
