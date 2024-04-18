package com.kss.service;

import com.kss.domains.Transaction;
import com.kss.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public void save (Transaction transaction){
        transactionRepository.save(transaction);
    }
}
