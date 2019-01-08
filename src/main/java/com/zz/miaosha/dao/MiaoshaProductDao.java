package com.zz.miaosha.dao;

import com.zz.miaosha.domain.MiaoshaProduct;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MiaoshaProductDao {

    @Select("select * from miaosha_product")
    public List<MiaoshaProduct> list();

    @Select("select * from miaosha_product where id= #{id}")
    public MiaoshaProduct getById(@Param("id") long id);

    @Insert("insert into miaosha_product(id, name, number) " +
            "values(#{id}, #{name}, #{number})")
    public int insertProduct(MiaoshaProduct product);

    @Update("update miaohsa_product set name=#{name}, number=#{number} where id =#{id}")
    public MiaoshaProduct update(MiaoshaProduct product);

    @Update("update miaosha_product set number = number - 1 where id = #{id} and number > 0")
    public int reduceStock(MiaoshaProduct g);

    @Update("update miaosha_product set number = #{number} where id = #{id}")
    public int resetStock(MiaoshaProduct g);
}
