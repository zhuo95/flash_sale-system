package com.zz.miaosha.dao;

import com.zz.miaosha.domain.MiaoshaOrder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MiaoshaOrderDao {

    @Select("select * from miaosha_order where id= #{id}")
    public MiaoshaOrder getById(@Param("id") long id);

    @Insert("insert into miaosha_order(id, user, product) " +
            "values(#{id}, #{user}, #{product})")
    public int insertOrder(MiaoshaOrder order);

    @Delete("delete from miaosha_order")
    public void deleteMiaoshaOrders();
}
