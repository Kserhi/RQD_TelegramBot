package com.botforuni.services;

import com.botforuni.domain.Statement;
import com.botforuni.domain.StatementInfo;
import com.botforuni.repositories.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StatementService {

    private final StatementRepository statementRepository;

    @Autowired
    public StatementService(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
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
    public  Long generateStatement(Long telegramId, String typeOfStatement) {

        Statement statement = new Statement();
        statement.setTelegramId(telegramId);
        statement.setTypeOfStatement(typeOfStatement);
        statement.setStatementInfo(StatementInfoService.generate(statement));

        statementRepository.save(statement);

        return statementRepository.findMaxId();
    }


    public   Statement findById(Long id)throws NoSuchElementException {
        Optional<Statement> statementOptional=statementRepository.findById(id);

        if (statementOptional.isEmpty()){
            throw new NoSuchElementException("Вудсутня довідка за telegramId: "+id);
        }
        return statementOptional.get();
    }

    public  void  save(Statement statement){
        statementRepository.save(statement);
    }

    public  void deleteById(Long id){
        statementRepository.deleteById(id);
    }

    public  List<Statement> getAllUserStatements(Long telegramId){

        return statementRepository.findAllByTelegramId(telegramId);

    }

    public  boolean userHaveStatement(Long telegramId){
        return statementRepository.countByTelegramId(telegramId) > 0;
    }

}
