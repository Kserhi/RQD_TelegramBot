package com.botforuni.services;

import com.botforuni.domain.Statement;
import com.botforuni.domain.StatementInfo;
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


    /**
     * Generates a new {@link Statement} based on the provided Telegram ID and type of statement.
     * It also initializes the associated {@link StatementInfo}, saves the statement to the database,
     * and returns the ID of the newly created statement.
     *
     * @param telegramId      the unique Telegram ID of the user to whom the statement belongs
     * @param typeOfStatement the type of the statement being generated
     * @return the ID of the newly created {@link Statement}
     */
    public static Long generateStatement(Long telegramId, String typeOfStatement) {

        Statement statement = new Statement();
        statement.setTelegramId(telegramId);
        statement.setTypeOfStatement(typeOfStatement);
        statement.setStatementInfo(StatementInfoService.generate(statement));

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
