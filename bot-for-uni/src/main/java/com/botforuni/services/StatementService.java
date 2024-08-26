package com.botforuni.services;

import com.botforuni.domain.Statement;
import com.botforuni.repositories.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementService {

    private static StatementRepository statementRepository;

    @Autowired
    public StatementService(StatementRepository statementRepository) {
        StatementService.statementRepository = statementRepository;
    }


    public static Long generateStatement(Long telegramId,String typeOfStatement){
            Statement statement =new Statement();
            statement.setTelegramId(telegramId);
            statement.setTypeOfStatement(typeOfStatement);

            statementRepository.save(statement);

            return statementRepository.findMaxId();
    }

    public  static Statement findById(Long id){
        return statementRepository.findById(id).get();
    }

    public static void  save(Statement statement){
        statementRepository.save(statement);
    }

    public static void deleteById(Long id){
        statementRepository.deleteById(id);
    }

}
