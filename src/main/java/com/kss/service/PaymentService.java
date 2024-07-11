package com.kss.service;

import com.kss.domains.Payment;
import com.kss.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void save(Payment payment){
        paymentRepository.save(payment);
    }
}
