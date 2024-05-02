package com.ffysVideo.mapper;

import com.ffysVideo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id} ")
    User getById(Long id);

    /**
     * 通过userName查询
     *
     * @param username
     * @return
     */
    @Select("select * from user where username=#{username} ")
    User getByUserName(String username);

    /**
     * 插入数据
     * @param user
     */
    @Insert("insert into user (name, username, password, phone, sex, status, create_time, update_time,img) " +
            "VALUES (#{name} ,#{username} ,#{password} ,#{phone} ,#{sex},#{status} ,#{createTime} ,#{updateTime} ,#{img} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(User user);

    List<User> selectByCondition(User user);
}
