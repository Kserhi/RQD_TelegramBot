package com.botforuni.services;

import com.botforuni.repositories.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StatementService {

    private static StatementRepository statementRepository;

    @Autowired
    public StatementService(StatementRepository statementRepository) {
        StatementService.statementRepository = statementRepository;
    }


    public static void generateStatement(Long telegramId,String typeOfStatement){


    }

}
