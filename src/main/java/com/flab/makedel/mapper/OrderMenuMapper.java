package com.flab.makedel.mapper;

import com.flab.makedel.dto.OrderMenuDTO;
import java.util.List;

public interface OrderMenuMapper {

    void insertOrderMenu(List<OrderMenuDTO> orderMenuList);

}
