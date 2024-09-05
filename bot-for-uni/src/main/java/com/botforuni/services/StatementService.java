package com.botforuni.services;

import com.botforuni.domain.Statement;
import com.botforuni.domain.StatementCache;
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




//    public   Statement findById(Long id)throws NoSuchElementException {
//        Optional<Statement> statementOptional=statementRepository.findById(id);
//
//        if (statementOptional.isEmpty()){
//            throw new NoSuchElementException("Вудсутня довідка за telegramId: "+id);
//        }
//        return statementOptional.get();
//    }

    public  void  save(Statement statement){
        statementRepository.save(statement);
    }


    public  List<Statement> getAllUserStatements(Long telegramId){

        return statementRepository.findAllByTelegramId(telegramId);

    }


    public Statement mapStatement(StatementCache statementCache){

        Statement statement =new Statement();

        statement.setFullName(statementCache.getFullName());
        statement.setYearEntry(statementCache.getYearEntry());
        statement.setGroupe(statementCache.getGroupe());
        statement.setPhoneNumber(statementCache.getPhoneNumber());
        statement.setFaculty(statementCache.getFaculty());
        statement.setTypeOfStatement(statementCache.getTypeOfStatement());
        statement.setTelegramId(statementCache.getId());
        return statement;
    }

}
