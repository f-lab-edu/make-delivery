package com.flab.makedel.event;

import com.flab.makedel.dao.CartItemDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class RollbackEventHandler {

    private final CartItemDAO cartItemDAO;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleEvent(RollbackEvent event) {
        cartItemDAO.insertMenuList(event.getUserId(), event.getCartList());
    }

}
