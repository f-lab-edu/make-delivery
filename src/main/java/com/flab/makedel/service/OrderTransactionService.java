package com.flab.makedel.service;

import com.flab.makedel.dto.CartItemDTO;
import com.flab.makedel.dto.CartOptionDTO;
import com.flab.makedel.dto.OrderDTO;
import com.flab.makedel.dto.OrderMenuDTO;
import com.flab.makedel.dto.OrderMenuOptionDTO;
import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.mapper.OrderMapper;
import com.flab.makedel.mapper.OrderMenuMapper;
import com.flab.makedel.mapper.OrderMenuOptionMapper;
import com.flab.makedel.utils.PayServiceFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    굳이 OrderTransactionService를 새로 만들은 이유 :
    우선 @Transactional은 AOP로 구현되어있어 RTW방식으로 프록시를 생성합니다.
    따라서 같은 서비스 내에 있는 메소드를 호출할때는 Transactional은 애노테이션이 적용되지 않습니다.
    지금 현재는 주문과 결제가 같은 트랜잭션에서 처리되야 하므로
    굳이 OrderTransactionService를 만들어서 트랜잭션을 분리할 필요는 없지만
    추후에 주문과 결제가 같은 트랜잭션에서 처리되지 않게 변경하게 될 수도 있으므로
    Transaction 전파 레벨이 변경되어야 할 수도 있다고 판단하여 분리하였습니다.
    전파레벨 설정을 기본값으로 해준 이유는 주문 트랜잭션안에서
    주문,결제가 모두 이루어져 커밋이 같이되어야하고, 롤백또한 같이 되어야하기 때문입니다.
    전파레벨 설정이 모두 기본값인 이유는 자식 트랜잭션이 부모 트랜잭션에 합류하여
    같은 트랜잭션에서 커밋,롤백이 되어야 하는 로직이기 때문입니다.
    주문과 결제가 동시에 이루어져야 합니다.
 */

@Service
@RequiredArgsConstructor
public class OrderTransactionService {

    private final OrderMapper orderMapper;
    private final OrderMenuMapper orderMenuMapper;
    private final OrderMenuOptionMapper orderMenuOptionMapper;
    private final PayServiceFactory payServiceFactory;

    @Transactional
    public long order(OrderDTO orderDTO, List<CartItemDTO> cartList,
        List<OrderMenuDTO> orderMenuList, List<OrderMenuOptionDTO> orderMenuOptionList) {

        orderMapper.insertOrder(orderDTO);
        long totalPrice = registerOrderMenu(cartList, orderDTO.getId(), orderMenuList,
            orderMenuOptionList);

        return totalPrice;
    }

    @Transactional
    public void pay(PayType payType, long totalPrice, long orderId) {

        PayService payService = payServiceFactory.getPayService(payType);
        payService.pay(totalPrice, orderId);

    }

    public long registerOrderMenu(List<CartItemDTO> cartList, Long orderId,
        List<OrderMenuDTO> orderMenuList, List<OrderMenuOptionDTO> orderMenuOptionList) {

        long totalPrice = 0;

        for (int i = 0; i < cartList.size(); i++) {
            CartItemDTO cart = cartList.get(i);
            totalPrice += cart.getPrice() * cart.getCount();

            OrderMenuDTO orderMenuDTO = OrderMenuDTO.builder()
                .orderId(orderId)
                .menuId(cart.getMenuId())
                .count(cart.getCount())
                .build();
            orderMenuList.add(orderMenuDTO);

            for (int j = 0; j < cart.getOptionList().size(); j++) {
                CartOptionDTO option = cart.getOptionList().get(j);
                totalPrice += option.getPrice();

                OrderMenuOptionDTO orderMenuOptionDTO = OrderMenuOptionDTO.builder()
                    .optionId(option.getOptionId())
                    .menuId(cart.getMenuId())
                    .orderId(orderId)
                    .build();
                orderMenuOptionList.add(orderMenuOptionDTO);
            }
        }

        orderMenuMapper.insertOrderMenu(orderMenuList);
        orderMenuOptionMapper.insertOrderMenuOption(orderMenuOptionList);

        return totalPrice;

    }

}
