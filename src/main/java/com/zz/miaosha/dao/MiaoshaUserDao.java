package com.zz.miaosha.dao;

import com.zz.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user where id= #{id}")
    public MiaoshaUser getById(@Param("id") long id);

    @Insert("insert into miaosha_user(id, nickname, password, salt, head, register_date, last_login_date, login_count) " +
            "values(#{id}, #{nickname}, #{password}, #{salt}, #{head}, #{registerDate}, #{lastLoginDate}, #{loginCount} )")
    public int insertUser(MiaoshaUser user);
}
