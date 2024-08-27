package com.botforuni.services;

import com.botforuni.domain.Statement;
import com.botforuni.repositories.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            StatementInfoService.save(StatementInfoService.generate(statement));

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

    public static List<Statement> getAllUserStatements(Long telegramId){

        return statementRepository.findAllByTelegramId(telegramId);

    }

    public static boolean userHaveStatement(Long telegramId){
        return statementRepository.countByTelegramId(telegramId) > 0;
    }

}
