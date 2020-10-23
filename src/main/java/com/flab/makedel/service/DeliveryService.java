package com.flab.makedel.service;

import com.flab.makedel.dao.DeliveryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryDAO deliveryDAO;

}
