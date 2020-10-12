package com.flab.makedel.event;

import com.flab.makedel.dto.CartItemDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RollbackEvent {

    private final String userId;
    private final List<CartItemDTO> cartList;

}
