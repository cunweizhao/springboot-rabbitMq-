package com.zcw.food.mapper;

import com.zcw.food.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderDetailMapper {
    int insert(OrderDetail record);

    List<OrderDetail> selectAll();
}
