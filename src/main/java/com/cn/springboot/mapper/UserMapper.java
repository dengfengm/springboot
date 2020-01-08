package com.cn.springboot.mapper;

import com.cn.springboot.bean.UserBean;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 获取所有用户
     *
     * @return
     */
    @Select(value = "select * from user")
    @Results(value = { @Result(column = "USER_ID", property = "userId", jdbcType = JdbcType.VARCHAR),
    				   @Result(column = "USER_NAME", property = "userName", jdbcType = JdbcType.VARCHAR)})
    List<UserBean> getUsers();

    /**
     * 修改用户信息
     *
     * @param user
     */
    @Update("update user set USER_NAME= #{userName} where USER_ID=#{userId}")
    void update(UserBean user);

    /**
     * 删除用户
     *
     * @param id
     */
    @Delete("delete from user where USER_ID=#{userId}")
    void del(String id);

    /**
     * 新增一条用户信息
     *
     * @param user
     */
    @Insert("insert into user(USER_NAME) values(#{userName})")
    @Options(useGeneratedKeys=true, keyProperty="userId", keyColumn="USER_ID")
    void save(UserBean user);

}
